package nf.co.rogerioaraujo;

import nf.co.rogerioaraujo.service.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {

        //SERVICOS
        SelectService select = new SelectService();
        SimpleService simpleCrud = new SimpleService();
        BlockingQueueOfferService offer = new BlockingQueueOfferService();
        BlockingQueuePutService put = new BlockingQueuePutService();
        ComThreadService threads = new ComThreadService();
        TruncateService database = new TruncateService();

        //SELECT SIMPLES
        select.buscar();

        //limpando tabela Usuario do banco de dados
        database.truncade();

        //INSERIR, ATUALIZAR E DELETAR - SIMPLES
        simpleCrud.inserirAtualizarDeletar();

        //limpando tabela Usuario do banco de dados
        database.truncade();

        //UTILIZANDO FILA BLOQUEANTE - OFFER
        offer.blockingQueueOffer();

        //limpando tabela Usuario do banco de dados
        database.truncade();

        //UTILIZANDO FILA BLOQUEANTE - PUT
        put.blockingQueuePut();

        //limpando tabela Usuario do banco de dados
        database.truncade();

        //UTILIZANDO THREADS
        threads.comThreads();

        //limpando tabela Usuario do banco de dados
        database.truncade();

    }
}
