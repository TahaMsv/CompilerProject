class Symbol{
    private String token;
    private Object value;

    public Symbol(String token,Object value){
        this.token=token;
        this.value=value;
    }

    public String getToken(){
        return this.token;
    } 

    public Object getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return this.token + " " + this.value.toString();
    }
}