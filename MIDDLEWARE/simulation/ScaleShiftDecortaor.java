package simulation;

public class ScaleShiftDecortaor implements RandomVariableGenerator {

    private double _shift, _scale;
    private RandomVariableGenerator _inner;
    public ScaleShiftDecortaor(RandomVariableGenerator inner, double shift, double scale){
        this._shift = shift;
        this._scale = scale;
        this._inner = inner;
    }

    public double getNextNumber(){
        return _scale * _inner.getNextNumber() + _shift;
    }

}
