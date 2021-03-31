import java.io.*;
import java.lang.reflect.Constructor;

class Arq<Generic extends Registro> {
    
    public String nomeArq;
    Constructor<Generic> constructor;
    private static int cabecario = 0;
    RandomAccessFile arqCompleto,
                     arqIndice;

    /**
	 * Construtor para abir um tipo de Arquivo opç de R/W.
	 * @param nomeArq Nome do arquivo
	 * @throws IOException Se o elemento existir.
	 */
    public Arq(Constructor<Generic> b, String nomeArq)throws IOException{
        this.nomeArq = nomeArq;
        this.constructor = b;
        arqCompleto = new RandomAccessFile(this.nomeArq, "rw");
        arqIndice = new RandomAccessFile("Indice"+this.nomeArq, "rw");
    }

     /**
	 * Metodo Privado para printar a Msg de como esta o Sistema.
	 * @param resp Boolean T/F
	 */
    private void printar(boolean resp){
        if(resp == true)
            MyIO.println("Ok");
        else 
            MyIO.println("Error");
    }

     /**
	 * Metodo Privado para determinar o tamanho de um Byte[].
	 * @param obj objeto generico
     * @return tamanho em int do vetor de Bytes
	 */
    public int lengthReg(Generic obj)throws IOException{
        byte[] b = obj.toByteArray();
        int resp = b.length;
        return resp;
    }

      /**
	 * Metodo Publico para criar um Obj como regitro.
	 * @param obj objeto generico
	 */
    public void Creat(Generic obj) throws IOException{
        MyIO.print("Creat: ");
        printar(creat(obj));
    }

    /**
	 * Metodo Privado para criar um Obj como regitro.
	 * @param obj objeto generico
     * @return bolleano
	 */    
    private boolean creat(Generic obj) throws IOException{
        int numRegistro;
        Boolean resp = false;
        try{
            arqCompleto.seek(cabecario);                    // Aponto para o cabeçario
            if(arqCompleto.length() > 0){                   // Busco pro um pra cabeçario Existente
                numRegistro = arqCompleto.readInt() +1;     // Caso exista Atualizo o numero de registro
            }                      
            else{
                numRegistro = 1;                    // Crio o primeiro registro
            }
          
                arqCompleto.seek(cabecario);                    // Aponto para o cabeçario
                arqCompleto.writeInt(numRegistro);              // Escrevo no arquivo o novo Numero de Registos
                obj.setId(numRegistro);                         // Crio o proximo ID. com base no N Registros.
                writeIndice(obj);
                writeObj(obj);                                  // Escrevo o Obj como Registro.
                resp = true;
            }catch(Exception  e){
                e.printStackTrace();
        }
        return resp;
    }
     /**
	 * Metodo Privado para escrever um Obj como regitro no Arquivo.
	 * @param obj objeto generico
	 */
    private void writeObj(Generic obj)throws IOException{
        arqCompleto.seek(arqCompleto.length());     // Aponto para a ultima posiçao do Arquivo.
        byte[] c = obj.toByteArray();               // Decodico em tipos primitivos.
        try{
            arqCompleto.writeBoolean(false);            // Escrevo a lapide
            arqCompleto.writeShort(c.length);           // Escrevo o tamanho em  bits do regitor
            arqCompleto.write(c);                       // Escrevo meu Registro
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    
    /**
     * Metodo Privado para escrever um Indice de Registro
     * @param obj obj generico
     */
    private void writeIndice(Generic obj)throws IOException{
        int pos =(int) arqCompleto.length();
        arqIndice.seek(arqIndice.length()); // Aponto para a ultima posiçao do Arquivo.
        arqIndice.writeInt(obj.getId());
        arqIndice.writeInt(pos);
    }

    /**
	 * Metodo Publico para lerr um Registro como Obj no Arquivo..
	 * @param Id objeto generico
	 */
    public Generic Read(int Id)throws Exception{
        MyIO.print("Read: ");
        Generic obj = constructor.newInstance(); 
        obj = read(Id);
        printar(VerificarNull(obj));
        return obj; 
    }

     /**
	 * Metodo Publico para verificar se existe um obj.
	 * @param obj objeto generico
	 */
    public boolean VerificarNull(Generic obj){
        Boolean resp = false;
        if(obj != null)
            resp = true;
        return resp; 
    }

    /**
	 * Metodo Privado para lerr um Registro como Obj no Arquivo.
	 * @param Id do registro
	 */

    private Generic read(int Id)throws Exception{
        Generic a = constructor.newInstance();                  // Nasce O obj
        Boolean lapide = false;                                 // Presumo q a Lapide esteja inoperante
        int pesquisaReg = sherch(Id),                           // Busco a Pos Operante
            pos = 0;
        arqIndice.seek(pesquisaReg);
        if(Id == arqIndice.readInt() && (pos = arqIndice.readInt()) != -1){
                arqCompleto.seek(pos);     
                lapide =  arqCompleto.readBoolean();                     // Lapide Recepe T/F
                if(lapide == false){
                    byte b[] = new byte[arqCompleto.readShort()];        // Crio um Byte[] com o seu tamanho. 
                    arqCompleto.read(b);
                    a.fromByteArray(b);                                  // Descarrego em Um obj
                    if(a.getId() == Id){                                // Verifico o Id.
                        return a;
                    }
                }
                MyIO.println("Alerta Falha de segurança\n \t Confira se o regitro completo esta deletado");
        }
        else{
            MyIO.println("Id nao encontrado no Indice.\n\t Verifique se os mesmo nao foi excluido\n Id ") ;
        }
        return null;
    }

    private int sherch(int id){
        int seekPos=0;
        id --;
            if(id != 0)
                seekPos = 8*id;
        return seekPos; 
    }


    /**
	 * Metodo Publico para atualizar um Registro como Obj no Arquivo..
	 * @param Id objeto generico
	 */
    public void Update(Generic obj)throws Exception{
        MyIO.print("Update: ");
        printar(update(obj)); 
    }

    /**
	 * Metodo private para atualizar um Registro como Obj no Arquivo..
	 * @param Id objeto generico
     * @return Boleeano se atualizou ou nao
	 */
    public boolean update(Generic obj)throws Exception{
        int pesquisaReg = sherch(obj.getId()),                           // Busco a Pos Operante
            pos = 0, tam =0;
        Generic obj1 = constructor.newInstance();
        Boolean resp = false;
        arqIndice.seek(pesquisaReg);
        if(obj.getId() == arqIndice.readInt() && (pos = arqIndice.readInt()) != -1){
            arqCompleto.seek(pos);
            if(arqCompleto.readBoolean() == false){
                tam = arqCompleto.readShort() ;                     // Tamanho do Registro
                byte b[] = new byte[tam];                   // Crio um Byte[] com o seu tamanho. 
                arqCompleto.read(b);
                obj1.fromByteArray(b);                  // Descarrego em Um obj
                if(obj1.getId() == obj.getId()){            // Verifico o Id.
                    if(lengthReg(obj1) >= lengthReg(obj)){
                        arqCompleto.seek(pos+3);
                        byte[] c = obj.toByteArray();           // Decodico em tipos primitivos.
                        arqCompleto.write(c);                           // Escrevo meu Registro
                    }else{
                            arqCompleto.seek(cabecario);                    // Aponto para o cabeçario
                            int numregistro = arqCompleto.readInt();
                            arqCompleto.seek(cabecario);                    // Aponto para o cabeçario
                            arqCompleto.writeInt(numregistro);
                            arqCompleto.seek(pos);
                            arqCompleto.writeBoolean(true);

                            UpdateI(pesquisaReg + 4);
                            writeObj(obj);
                    }
                    resp = true;
                }
            }

        }
   
        return resp;
    }

    private void UpdateI(int a)throws IOException{
        int pos =(int) arqCompleto.length();
        arqIndice.seek(a);
        arqIndice.writeInt(pos);
    }
    
     /**
	 * Metodo Publico para deletar um Registrono Arquivo..
	 * @param Id objeto generico
	 */
    public void Delete(int Id)throws Exception{
        MyIO.print("Delete: ");
        printar(delete(Id));
    }

    /**
	 * Metodo private para deletar um Registro no Arquivo.
	 * @param Id objeto generico
     * @return Boleeano se atualizou ou nao
	 */
    private Boolean delete(int Id)throws Exception{
        Boolean resp = false;                                 // Presumo q a Lapide esteja inoperante
        int pesquisaReg = sherch(Id),                           // Busco a Pos Operante
            pos = -1;

        arqIndice.seek(pesquisaReg);
        if(Id == arqIndice.readInt() && (pos = arqIndice.readInt()) != -1){
            arqIndice.seek(pesquisaReg + 4);
            arqIndice.writeInt(-1); 
            arqCompleto.seek(pos);
            arqCompleto.writeBoolean(true);
            resp = true;

        }else{
        MyIO.println("Id nao encontrado ou ja excluido");
        }        
       
        return resp;
    } 

    public void Close()throws Exception{
        arqCompleto.close();
        arqIndice.close();
    }

}
