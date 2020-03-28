import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.lang.*;
import java.io.*;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Carteira {

    public static void main(String[] args) throws Exception{
        
        ArrayList<Empresa> listar = new ArrayList<Empresa>();
        // Cria ArrayList que receberá dados do arquivo csv
        
        listar = carregaLista();
        // processa os dados e passa para o arraylist;


        System.out.println(" ***** CARTEIRA DE ACOES *****\n");
        System.out.println("    As empresas que atualmente compõem sua carteira são: \n");

        boolean continua = true;
        do {

        System.out.println(" ");
        System.out.println(" [ 1 ] - Imprimir carteira de ações");
        System.out.println(" [ 2 ] - Recarrega Lista;");
        System.out.println(" [ 3 ] - Adicionar empresa a carteira;");
        System.out.println(" [ 4 ] - Salvar para o arquivo;");
        System.out.println(" [ 5 ] - Fazer um aporte;");
        System.out.println(" [ 6 ] - calcula lucro de uma única empresa;");
        System.out.println(" [ 7 ] - Calcula lucro de toda a carteira;");
        System.out.println(" [ 8 ] - Imprime informações das Empresas;");
        System.out.println(" [ 9 ] - Excluir / vender empresa;");
        System.out.println(" [ 0 ] - Sair");
        System.out.println("\n Digite a opção desejada e tecle enter:");

        int escolha = digitaInteiro();

        switch (escolha) {
            case 1:
                imprimeLista(listar);
                break;
            case 2:
                listar = carregaLista();
                break;
            case 3:
                System.out.println(" Digite o Ticket da nova empresa: ");
                String sigla = digitaString();
                Empresa novaEmpresa = new Empresa();
                novaEmpresa = adicionarEmpresaALista(sigla);
                listar.add(novaEmpresa);
                break;
            case 4:
                gravaArquivo(listar);
                break;
            case 5:
                aportar(listar);
                break;
            case 6:
                lucroUnico(listar);
                break;
            case 7:
                lucroTotal(listar);
                break;
            case 8:
                imprimeInformacoesEmpresas(listar);
                break;
            case 9:
                System.out.println(" Digite 1 para vender e 2 para excluir");
                int opcao = digitaInteiro();
                    if (opcao == 2){
                        excluiEmpresa(listar);
                    } else {
                        //vendeEmpresa();
                    }
                
                break;
            case 0: 
                System.out.println(" \n Deseja salvar as alterações feitas na carteira? ");
                System.out.println(" Digite 1 para sim e 0 para não:");
                int salvar = digitaInteiro();
                if (salvar == 1){
                    gravaArquivo(listar);
                    continua = false;
                } else {
                    continua = false;
                }
                break;
            default:
                System.out.println(" Opção inválida, por favor digite novamente: ");
                System.out.println(" *****  *****   *****S");
                break;
        
        }
        } while( continua == true);
       // gravaArquivo(listar);

    }

    public static void excluiEmpresa(ArrayList<Empresa> lista){
        System.out.println(" Qual empresa deseja excluir? (Digite o ticket da Empresa, ex: ABCD5)");
        String nome = digitaString();
        boolean encontrou = false;
        int contador = 0;

        // A empresa será encontrada pelo nome que foi digitada na String nome;
        // O contador serve para contar em que posição do Array ele está;

        for (Empresa o : lista) {
            int comparacao = (nome.compareTo(o.getTicket()));
            if (comparacao == 0){
                encontrou = true;
                break; // Se a empresa foi achada quebramos o loop, não se precisa mais incrementar o contador 
            }
            contador++;
        }

        if (!encontrou){
            System.out.println(" Empresa não encontrada na sua carteira;");
            System.out.println(" Por favor tente novamente;");
        } else {
            lista.remove(contador); // Remove a empresa da exata posição em que o contador parou de contar;
            System.out.println(" A empresa foi excluida da sua carteira;");
        }



    }

    public static void imprimeInformacoesEmpresas(ArrayList<Empresa> lista) {
        for (Empresa o : lista) {
            System.out.println(o.toString());
        }
    }

    public static void lucroTotal(ArrayList<Empresa> lista){
        System.out.println("    Atualize a cotação Atual de cada empresa: ");
        double total = 0;
        for (Empresa o : lista) {
            System.out.println(" Cotação atual da empresa " + o.getTicket());
            double cotacao = digitaDouble();
            o.setCotAtual(cotacao);
            total = total + o.calculaLucro();
        }
        System.out.println("___________________________________________________");
        System.out.println( " O rendimento total da carteira é de: " + total);
        System.out.println("___________________________________________________");
    }

    public static void lucroUnico(ArrayList<Empresa> lista){
        System.out.println(" Qual empresa deseja calcular o lucro? (Digite o ticket da Empresa, ex: ABCD5)");
        String nome = digitaString();
        boolean encontrou = false;

        for (Empresa o : lista) {
            int comparacao = (nome.compareTo(o.getTicket()));
            if (comparacao == 0){
                System.out.println(" Digite o valor da Cotação Atual desta empresa");
                o.setCotAtual(digitaDouble());
                System.out.println(" O lucro da empresa é de: " + o.calculaLucro());
                encontrou = true;
            }
        }

        if (!encontrou){
            System.out.println(" Empresa não encontrada na sua carteira;");
            System.out.println(" Por favor tente novamente;");
        }

    }


    public static void aportar (ArrayList<Empresa> lista) throws Exception{
        System.out.println(" \n     Em qual empresa deseja aportar? (Digite o ticket da empresa)");
        String empresa = digitaString();
        boolean alteracaoFeita = false;

        for (Empresa o : lista) {
            int comparacao = (empresa.compareTo(o.getTicket()));
            if (comparacao == 0){
                System.out.println(" Qual valor foi o valor aportado?");
                double newAporte = digitaDouble();
                System.out.println(" Quantas ações foram compradas com esse valor?");
                int novasAcoes = digitaInteiro();
                o.aporte(newAporte, novasAcoes);
                gravaArquivo(lista);
                System.out.println(" Aporte realizado com Sucesso!");
                alteracaoFeita = true;
            }
        }

        if (!alteracaoFeita){
            System.out.println(" Empresa não encontrada na sua carteira, deseja adicionar esta empresa a lista e ao arquivo?");
            System.out.println(" Ditie 1 para sim e 0 para não;");
            int adiciona = digitaInteiro();
            if (adiciona == 1){

                Empresa novaEmpresa = new Empresa();
                novaEmpresa = adicionarEmpresaALista(empresa);
                lista.add(novaEmpresa);
                gravaArquivo(lista);
            }
        }
    }

    public static void imprimeLista(ArrayList<Empresa> lista) throws Exception{
        int posicao = 0;

        System.out.println( " \n Sua Carteira de ações é composta por: \n");
        for (Empresa o : lista){
            System.out.println(" " + posicao + " - " + o.getTicket() + " - " + o.getNome());
            posicao++;
        }
    }

    public static void gravaArquivo(ArrayList<Empresa> lista) throws Exception{
        int volta  = 0;

        for (Empresa o : lista) {
            if (volta == 0){
                FileWriter fw = new FileWriter("Carteira.csv");

                fw.write(o.salvaGuard());
                fw.write(System.lineSeparator());

                fw.close();
            } else {
                FileWriter fw = new FileWriter("Carteira.csv", true);

                fw.write(o.salvaGuard());
                fw.write(System.lineSeparator());

                fw.close(); 
            }
            volta++;
        }
    }

    public static Empresa adicionarEmpresaALista(String ticket){
            // Adiciona uma empresa a lista que está sendo usada;

        Empresa empr = new Empresa();

        System.out.println("    Digite o nome da empresa: ");
        String nome = digitaString();
        empr.setNome(nome);

        System.out.println("    Digite o preço médio de entrada desta empresa: ");
        double pmedio = digitaDouble();
        empr.setPrecoMedio(pmedio);

        System.out.println("    Digite a quantidade de ações que você tem: ");
        int quant = digitaInteiro();
        empr.setQuantPapeis(quant);

        empr.setTicket(ticket);

        System.out.println("    Digite o valor aportado: ");
        double aportes = digitaDouble();
        empr.setValorAportado(aportes);

        System.out.println(empr.toString());
        
        return empr;
    }

    public static ArrayList<Empresa> carregaLista() throws Exception{
        
        try {
        ArrayList<Empresa> lista = new ArrayList<Empresa>();

        Scanner scann = new Scanner(new File("Carteira.csv"));
        // Cria o objeto Scanner que receberá o arquivo csv;


        while (scann.hasNextLine()){
            String linha = scann.nextLine();
            //Cria a String que recebe a linha inteira
            Scanner linhaEscaner = new Scanner(linha);
            // Objeto que recebe a linha para tratamento;
            linhaEscaner.useLocale(Locale.US);
            linhaEscaner.useDelimiter(",");

            String ticket = linhaEscaner.next();

            String pmedioBruta = linhaEscaner.next();
            String pmedioTratada = pmedioBruta.replaceAll(" ", "");
            Double pmedio = Double.parseDouble( pmedioTratada);

            String aporteBruto = linhaEscaner.next();
            String aporteTratado = aporteBruto.replaceAll(" ", "");
            Double aporte = Double.parseDouble(aporteTratado);

            String acoesBruta = linhaEscaner.next();
            String acoesTratada = acoesBruta.replaceAll(" ", "");
            Integer acoes = Integer.parseInt(acoesTratada);

            String nome = linhaEscaner.next();

            Empresa empresa = new Empresa();
            empresa.setNome(nome);
            empresa.setTicket(ticket);
            empresa.setPrecoMedio(pmedio);
            empresa.setQuantPapeis(acoes);
            empresa.setValorAportado(aporte);
            lista.add(empresa);
            linhaEscaner.close();

        }
        scann.close();

        //for (Empresa o : lista){
        //    System.out.println(o.getTicket() + " - " + o.getNome());
        //}

        //chamaMenu();
        return lista;
        } catch (FileNotFoundException ex){
            System.out.println(" Não foi encontrado um arquivo de Carteira.");
            FileWriter fw = new FileWriter("Carteira.csv");
            fw.close();
            System.out.println(" Um novo arquivo Carteira vazio foi criado;");
            ArrayList<Empresa> lista = new ArrayList<Empresa>();
            return lista;
        }

    }



    public static String digitaString() {
        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        return texto;
    }

    public static int digitaInteiro() {
        Scanner sc = new Scanner(System.in);
        int inteiro = sc.nextInt();
        return inteiro;
    }

    public static double digitaDouble() {
        Scanner sc = new Scanner(System.in);
        double doufrac = sc.nextDouble();
        return doufrac;
    }
 
}