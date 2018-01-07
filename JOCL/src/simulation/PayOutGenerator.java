package simulation;

import java.util.Random;
import org.jocl.*;
import static org.jocl.CL.*;

public class PayOutGenerator {
	private int _batchSize = 1000000; // Batch size
	private EuropeanOption _option;
	
	/**
	 * Constructs a PayOutGenerator
	 * @param option 
	 */
	public PayOutGenerator (EuropeanOption option) {
		_option = option;
	}
	
				
	/**
	 * creates a long batches of numbers, 1M uniform number
	 * @return a Uniform Vector
	 */
    private float[] getUniformVector() {
    	float[] vector=new float[_batchSize];
        Random rd=new Random();
        for (int i=0 ; i< _batchSize ; i++) 
        	vector[i] = rd.nextFloat();
        return vector;  	
    }
    
	/**
	 * creates two long batches of payouts, 1M each
	 * @return 2 dimensional array of payouts
	 */    
    protected float[][] getPayOut(){
    	// Get the basic information
        cl_platform_id[] platforms = new cl_platform_id[1];
        clGetPlatformIDs(1, platforms,null);
        cl_platform_id platform = platforms[0];
        cl_device_id[] devices = new cl_device_id[1];
        clGetDeviceIDs(platform,  CL_DEVICE_TYPE_GPU, 1, devices, null);
        //System.out.println(devices);
        cl_device_id device = devices[0];
        
        // Initialize the context properties
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);

        // Create a context for the selected device
        cl_context context = clCreateContext(
                contextProperties, 1, new cl_device_id[]{device},
                null, null, null);
        
        // Create a command-queue for the selected device
        cl_command_queue commandQueue = clCreateCommandQueue(context, device, 0, null);
        

		String src = "__kernel void get_final_payout(__global const float* in1, __global const float* in2, __global float* out1,__global float* out2, float term, float r, float sigma, float initial, float strike, float type, float discount_factor) \n" +
				"{\n" + 
				"	#ifndef M_PI\n" + 
				"	#define M_PI 3.14159265358979323846\n" + 
				"	#endif\n" + 
				"	int i = get_global_id(0);\n" + 
				//Box Muller transformation
				"\n" + 
				"	out1[i] = sqrt(-2*log(in1[i]))*cos(2*M_PI*in2[i]);\n" + 
				"	out2[i] = sqrt(-2*log(in1[i]))*sin(2*M_PI*in2[i]);\n" + 
				//simulate final day price(log normal)
                "\n" +
                "   out1[i] = initial* exp((r - sigma*sigma/2)*term+sigma*out1[i]*sqrt(term));\n" + 
                "   out2[i] = initial* exp((r - sigma*sigma/2)*term+sigma*out2[i]*sqrt(term));\n" + 
                "\n" +
                //payoff at expiration
                "   out1[i] = max(type*(out1[i]-strike),0.0);\n" + 
                "   out2[i] = max(type*(out2[i]-strike),0.0);\n" + 
                "\n" +
                //discounted value
                "   out1[i] =  out1[i] * discount_factor;\n" + 
                "   out2[i] =  out2[i] * discount_factor;\n" + 
				"}";


        // Create the program from the source code
        cl_program program = clCreateProgramWithSource(context, 1, new String[]{ src }, null, null);

        // Build the program
        clBuildProgram(program, 0, null, null, null, null);

        // Create the kernel
        cl_kernel kernel = clCreateKernel(program, "get_final_payout", null);
        
//        final long tmp = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis());
        
        float srcArrayIn1[] = getUniformVector();
        float srcArrayIn2[] = getUniformVector();
        float dstArrayOut1[] = new float[_batchSize];
        float dstArrayOut2[] = new float[_batchSize];

        Pointer srcIn1 = Pointer.to(srcArrayIn1);
        Pointer srcIn2 = Pointer.to(srcArrayIn2);
        Pointer dstOut1 = Pointer.to(dstArrayOut1);
        Pointer dstOut2 = Pointer.to(dstArrayOut2);       

        // Allocate the memory objects for the input- and output data
        cl_mem memObjects[] = new cl_mem[5];
        memObjects[0] = clCreateBuffer(context,
           		CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_float * _batchSize, srcIn1, null);
        memObjects[1] = clCreateBuffer(context,
        		CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_float * _batchSize, srcIn2, null);
        memObjects[2] = clCreateBuffer(context,
           		CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_float * _batchSize, dstOut1, null);
        memObjects[3] = clCreateBuffer(context,
           		CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_float * _batchSize, dstOut2, null);

//        System.out.println(System.currentTimeMillis()-tmp);

        // Set the arguments for the kernel
        clSetKernelArg(kernel, 0,
                Sizeof.cl_mem, Pointer.to(memObjects[0]));
        clSetKernelArg(kernel, 1,
                Sizeof.cl_mem, Pointer.to(memObjects[1]));
        clSetKernelArg(kernel, 2,
                Sizeof.cl_mem, Pointer.to(memObjects[2]));
        clSetKernelArg(kernel, 3,
                Sizeof.cl_mem, Pointer.to(memObjects[3]));
        clSetKernelArg(kernel, 4,
        		Sizeof.cl_float, Pointer.to(new float[] {_option.getTime()}));
        clSetKernelArg(kernel, 5,
        		Sizeof.cl_float, Pointer.to(new float[] {_option.getInterestRate()}));
        clSetKernelArg(kernel, 6,
        		Sizeof.cl_float, Pointer.to(new float[] {_option.getVolatility()}));
        clSetKernelArg(kernel, 7,
        		Sizeof.cl_float, Pointer.to(new float[] {_option.getSpotPrice()}));
        clSetKernelArg(kernel, 8,
        		Sizeof.cl_float, Pointer.to(new float[] {_option.getStrikePrice()}));
        clSetKernelArg(kernel, 9,
        		Sizeof.cl_float, Pointer.to(new float[] {(float)_option.getTypeToInt()}));
        clSetKernelArg(kernel, 10,
        		Sizeof.cl_float, Pointer.to(new float[] {_option.getDiscountFactor()}));
//        System.out.println((System.currentTimeMillis() - tmp));

        // Set the work-item dimensions
        long global_work_size[] = new long[]{_batchSize};
        long local_work_size[] = new long[]{1};

        // Execute the kernel
        clEnqueueNDRangeKernel(commandQueue, kernel, 1, null,
                global_work_size, local_work_size, 0, null, null);

        // Read the output data
        clEnqueueReadBuffer(commandQueue, memObjects[3], CL_TRUE, 0,
        		_batchSize * Sizeof.cl_float, dstOut2, 0, null, null);
        clEnqueueReadBuffer(commandQueue, memObjects[2], CL_TRUE, 0,
        		_batchSize * Sizeof.cl_float, dstOut1, 0, null, null);
        clEnqueueReadBuffer(commandQueue, memObjects[1], CL_TRUE, 0,
        		_batchSize * Sizeof.cl_float, srcIn2, 0, null, null);
        clEnqueueReadBuffer(commandQueue, memObjects[0], CL_TRUE, 0,
        		_batchSize * Sizeof.cl_float, srcIn1, 0, null, null);
		
//        for ( int i = 0; i < 10; ++i){
//            System.out.println( "In: "+srcArrayIn1[i] + "," + srcArrayIn2[i] + "\nOut: " + dstArrayOut1[i]+ "," +dstArrayOut2[i] );
//        }
		return new float[][]{dstArrayOut1,dstArrayOut2};
		
    }
	
	

	
	
}