
/*-
 * Classname:             SingleInstance.java
 *
 * Version information:   1.0
 *
 * Date:                  09/01/2013 - 16:09:39
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.swing.JOptionPane;

/**
 * Classe responsável pelo controle de permissão de uma única instancia da
 * aplicação
 *
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SingleInstance {

    //arquivo de verificação
    private static File file;
    //canal
    private static FileChannel fileChannel;
    //trava
    private static FileLock fileLock;

    /**
     * Verifica se uma instancia da aplicação já esta aberta
     */
    public static void check() {
        try {
            file = new File("running.lock");
            // checa se o lock existe
            if (file.exists()) {
                // se existe tenta deleta-lo
                file.delete();
            }
            // Tenta obter o bloqueio do arquivo
            fileChannel = new RandomAccessFile(file, "rw").getChannel();
            fileLock = fileChannel.tryLock();
            if (fileLock == null) {
                //o arquivo já voi bloqueado por outra aplicação
                fileChannel.close();
                System.err.println("Erro: O Programa já está em execução");
                System.exit(0);
            }
            // Adiciona um hook para liberar o bloqueio da aplicação quando ele é encerrada
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException interruptedException) {
                System.err.println("Erro: com thread de single instance");
                interruptedException.printStackTrace();

                LogMaker.create(interruptedException);
            }
        } catch (IOException iOException) {
            System.err.println("Erro: O Programa já está em execução");
            iOException.printStackTrace();
            JOptionPane.showMessageDialog(null, "O programa já esta em execução!", "Erro!", JOptionPane.INFORMATION_MESSAGE);
            LogMaker.create(iOException);
            System.exit(0);

        }
    }

    public static void unlockFile() {
        // libera e deleta o arquivo de lock
        try {
            if (fileLock != null) {
                fileLock.release();
            }
            fileChannel.close();
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ShutdownHook extends Thread {

        public void run() {
            unlockFile();
        }
    }

    public static void main(String[] args) {
        try {
            if (SingleInstanceApplication.check()) {
                //bloqueia o arquivo
                SingleInstanceApplication.lock();
                Thread.sleep(100000);

            } else {
                //se o arquivo está em uso não permite o acessoa ao programa
                System.out.println("O programa já foi aberto!");
                JOptionPane.showMessageDialog(null, "O programa já está em execuação!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
