class Interface {

    public static int Inter(){
        MyIO.setCharset("Utf-8");;
        MyIO.println("\tPerguntas 1.0");
        MyIO.println("===================================");
        MyIO.println("ACESSO");
        MyIO.println("1) Acesso ao Sistema \n2) Novo Usuario (Primeiro Acesso) \n0) Sair");
        MyIO.println("Opção: ");
        int dado = MyIO.readInt();
        return dado;
    }

    public static User newUser(){
        User novoUser = new User();
        while(!newUser(novoUser)) MyIO.println("Erro\n Senhas nao conferem ou Email Ivalido");
        return novoUser;
    }

    private static boolean newUser(User novoUser){
        Boolean resp = false;
        String conferir = "";
        MyIO.println("Novo ascesso ao sistema para Proseguir informe:");
        MyIO.println("Nome:");
            novoUser.setNome(MyIO.readLine());
        MyIO.println("E-mail:");
            novoUser.setemail(MyIO.readLine());
        MyIO.println("Senha:");
            novoUser.setsenha(MyIO.readLine());
        MyIO.println("Confirmar Senha:");
            conferir = MyIO.readString();
        

        if(conferir.equals(novoUser.getSenha()) && novoUser.getEmail().contains("@"))
            resp = true;
        
        return resp;
    }





    
}
