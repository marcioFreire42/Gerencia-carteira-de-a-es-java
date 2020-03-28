import java.text.DecimalFormat;

public class Empresa {
    private String ticket;
    private String nome;
    private int quantPapeis;
    private double precoMedio;
    private double valorAportado;
    private double cotAtual;

    public Empresa(){}

    //SETTERS

    public void setCotAtual(double cotacao){
        this.cotAtual = cotacao;
    }

    public void setNome(String name){
        this.nome = name;
    }

    public void setTicket(String tick) {
        this.ticket = tick;
    }

    public void setQuantPapeis(int quantidade){
        this.quantPapeis = quantidade;
    }

    public void setPrecoMedio(double pm){
        this.precoMedio = pm;
    }
    
    public void setValorAportado(double aporte){
        this.valorAportado = aporte;
    }

    // GETTERS

    public double getCotAtual(){
        return this.cotAtual;
    }

    public String getNome(){
        return this.nome;
    }

    public String getTicket(){
        return this.ticket;
    }

    public int getQuantPapeis(){
        return this.quantPapeis;
    }

    public double getPrecoMedio(){
        return this.precoMedio;
    }

    public double getValorAportado(){
        return this.valorAportado;
    }

    public String salvaGuard(){
        // Este método cria a linha de texto em String que será salva no arquivo;
        // Os atributos são colocados na posição necessário para o correto carregamento do arquivo;
        return (this.ticket + ", " + this.precoMedio + ", " + this.valorAportado + ", " + this.quantPapeis + "," + this.nome);
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("00.00");
        return (this.ticket + " - PM de " + df.format(this.precoMedio) + 
        ", R$ " + df.format(this.valorAportado) + " de valor aportado, " + this.quantPapeis + " ações, " + this.nome);
    }

    public void aporte(double vaportado, int papeis){
        this.valorAportado = this.valorAportado + vaportado;
        this.quantPapeis = this.quantPapeis + papeis;
        this.precoMedio = valorAportado/quantPapeis; 
        // Realiza um aporte à empresa em questão, 
        // Onde atualiza o valor aportado, a quantidade de ações agora e o novo preço médio;
    }

    public double calculaLucro(){
        double lucro = (this.cotAtual * this.quantPapeis) - this.valorAportado;
        return lucro;

        // Calcula o lucro com base na cotação atual multiplicada pela quantidade de papéis menos o valor aportado;
    }
}