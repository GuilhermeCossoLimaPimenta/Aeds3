import java.io.*;

class Main {
    public static void main(String[] args)throws IOException{
        int dado;
        Arq<User> arquivoUser;
        try{
           arquivoUser = new Arq<User>(User.class.getConstructor(), "User.db");
            while((dado = Interface.Inter()) != 0){
                switch(dado) {
                    case 1:

                        break;                
                    case 2:
                        User novo = Interface.newUser();
                        arquivoUser.Creat(novo);                        
                        break;
                    default:
                        MyIO.println("Dado invalido\n Tente Novamente");
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        


        
        //Arq<Ator> arquivoAtor;    
        /*
        Ator a1= new Ator(1, "Robert Downey Jr",75.5F, 1.70F,"Nova Iorque", "Nova York");
        Ator a2= new Ator(2, "Chris Evans",80.0F, 1.83F,"Boston", "Massachusetts");
        Ator a3= new Ator(1, "Robert Downey Jr",75.5F, 1.70F,"Newww Iorque", "Nova York");
        Ator a4 = new Ator();
        */
     
/*
        try{
            arquivoUser = new Arq<User>(User.class.getConstructor(), "User.db");
            arquivoUser.Creat(u1);
            arquivoUser.Creat(u3);
            arquivoUser.Creat(u2);
            u2.ImprimirUser(arquivoUser.VerificarNull(teste)); 
            MyIO.println();
            arquivoUser.Delete(3);
            MyIO.println();
            teste = arquivoUser.Read(2);
            teste.ImprimirUser(arquivoUser.VerificarNull(teste));           
            MyIO.println();
            arquivoUser.Update(u5);
            teste = arquivoUser.Read(2);
            teste.ImprimirUser(arquivoUser.VerificarNull(teste));
            MyIO.println();
            arquivoUser.Update(u4);
            teste = arquivoUser.Read(2);
            teste.ImprimirUser(arquivoUser.VerificarNull(teste));
            arquivoUser.Close();           
        }catch(Exception e){
            e.printStackTrace();
        }
   */
    }

}
