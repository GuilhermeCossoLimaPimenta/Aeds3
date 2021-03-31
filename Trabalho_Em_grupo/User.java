import java.io.*;

class User implements Registro {

    private int     ID;
    private String  nome, 
                    email,
                    senha;

    //Costrutor
    public User(){
        this.ID = -1;
        this.nome = "";
        this.email = "";
        this.senha = "";
    }

    public User(String nome, String email, String senha){  
        this.ID = -1;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    public User(int id,String nome, String email, String senha){  
        this.ID = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

  

    // ...........SET............
    public void setId(int ID) {
        this.ID = ID;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setemail(String cidadeNascimeto) {
        this.email = cidadeNascimeto;
    }
    public void setsenha(String senha) {
        this.senha = senha;
    }
    
    //.............GETS...................
    public int getId() {
        return ID;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }

    // Imprimir o Resultado
    public void imprimir(){
        MyIO.println("ID: " + ID + ".\nNome: "+ nome + " .\nCidade de Nascimento: " + email + ".\nEstado de Nascimento: "+ senha + ".");
    }
    
    
    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(ID);
        dos.writeUTF(nome);
        dos.writeUTF(email);
        dos.writeUTF(senha);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        ID = dis.readInt();
        nome = dis.readUTF();;
        email = dis.readUTF();
        senha = dis.readUTF();
    }

    public void ImprimirUser(boolean a){
        MyIO.print("Dados do Obj: ");
        if(a != false)
             MyIO.println("ID: " +ID + ".\nNome: "+ nome +".\nE-mail: "+ email + ".\nSenha: "+ senha +".\n");
        else
            MyIO.println("Obj Nulo\n");
    }
}
