package com.vaklinov.zcashui;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitorInputStream;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.vaklinov.zcashui.OSUtil.OS_TYPE;

/**
 * Fetches the proving key.  Deliberately hardcoded.
 * @author zab
 */
public class ProvingKeyFetcher {

    //private static final Logger LOG = Logger.getLogger(ProvingKeyFetcher.class.getName());


    private static final int PROVING_KEY_SIZE = 910173851;
    private static final String SHA256 = "8bc20a7f013b2b58970cddd2e7ea028975c88ae7ceb9259a5344a16bc2c0eef7";
    private static final String URL = "https://z.cash/downloads/sprout-proving.key";
    // TODO: add backups

    public void fetchIfMissing(StartupProgressDialog parent) throws IOException {
        try {
            verifyOrFetch(parent);
        } catch (InterruptedIOException iox) {
            JOptionPane.showMessageDialog(parent, "Komodo cannot proceed without a proving key.");
            System.exit(-3);
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


    private void verifyOrFetch(StartupProgressDialog parent) throws IOException {
        System.out.println("verify or fetch function called");

        File walletDAT = new File(OSUtil.getBlockchainDirectory() + "/wallet.dat");

        File zCashParams = new File(OSUtil.getZcashParamsDirectory());
        File komodoAppDir = new File(OSUtil.getBlockchainDirectory());

        zCashParams = zCashParams.getCanonicalFile();
        komodoAppDir = komodoAppDir.getCanonicalFile();

        boolean needsFetch = false;

        if (!zCashParams.exists()) {

            System.out.println(zCashParams.getCanonicalPath()+" did not exist");

            needsFetch = true;
            zCashParams.mkdirs();
        }

        if (!komodoAppDir.exists()){
          System.out.println(komodoAppDir.getCanonicalPath()+" did not exist");
          komodoAppDir.mkdirs();
        }

        File komodoConfigFile = new File(komodoAppDir,"komodo.conf");
        komodoConfigFile = komodoConfigFile.getCanonicalFile();
        if(!komodoConfigFile.exists()) {
          System.out.println("komodo config file does not exist");
          JOptionPane.showMessageDialog(parent, "Komodo needs to create the komodo.conf file. This will happen only once.\n  "
                  + "Press OK to continue");

          try{

            PrintWriter writer = new PrintWriter(komodoConfigFile);
            writer.println("rpcuser=komodorpcuser");
            writer.println("rpcpassword="+getSaltString());
            writer.println("daemon=1");
            writer.println("rpcallowip=127.0.0.1");
            writer.println("rpcbind=127.0.0.1");
            writer.println("server=1");
            writer.println("addnode=5.9.102.210");
            writer.println("addnode=78.47.196.146");
            writer.println("addnode=178.63.69.164");
            writer.println("addnode=88.198.65.74");
            writer.close();

          } catch (IOException e) {

            System.out.println("ERRR: "+e.toString());
          }

        }

        // verifying key is small, always copy it
        File verifyingKeyFile = new File(zCashParams,"sprout-verifying.key");
        FileOutputStream fos = new FileOutputStream(verifyingKeyFile);
        InputStream is = ProvingKeyFetcher.class.getClassLoader().getResourceAsStream("sprout-verifying.key");
        copy(is,fos);
        fos.close();

        File provingKeyFile = new File(zCashParams,"sprout-proving.key");
        provingKeyFile = provingKeyFile.getCanonicalFile();
        if (!provingKeyFile.exists()) {
            System.out.println("proving key file does not exist");
            needsFetch = true;
        } else if (provingKeyFile.length() != PROVING_KEY_SIZE) {
            System.out.println("proving key file is wrong size "+provingKeyFile.length());
            needsFetch = true;
        } else {
            parent.setProgressText("Verifying proving key...");
            needsFetch = !checkSHA256(provingKeyFile,parent);
            if (needsFetch)
                System.out.println("proving key SHA 256 did not match");
        }

        if (!needsFetch) {
            System.out.println("no need to fetch proving key file");
            return;
        }

        JOptionPane.showMessageDialog(parent, "Komodo needs to download a large file.  This will happen only once.\n  "
                + "Please be patient.  Press OK to continue");

        parent.setProgressText("Downloading proving key...");
        provingKeyFile.delete();
        OutputStream os = new BufferedOutputStream(new FileOutputStream(provingKeyFile));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(URL);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            is = response.getEntity().getContent();
            ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(parent, "Downloading proving key", is);
            pmis.getProgressMonitor().setMaximum(PROVING_KEY_SIZE);
            pmis.getProgressMonitor().setMillisToPopup(10);

            System.out.println("starting fetch of proving key file");
            copy(pmis,os);
            os.close();
            System.out.println("finished fetch of proving key file");
        } finally {
            try {if (response != null)response.close();} catch (IOException ignore){}
            try {httpClient.close();} catch (IOException ignore){}
        }
        parent.setProgressText("Verifying downloaded proving key...");
        if (!checkSHA256(provingKeyFile, parent)) {
            System.out.println("fetched proving key file failed checksum");
            JOptionPane.showMessageDialog(parent, "Failed to download proving key.  Cannot continue");
            System.exit(-4);
        }
    }


    private static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[0x1 << 13];
        int read;
        while ((read = is.read(buf)) >- 0) {
            os.write(buf,0,read);
        }
        os.flush();
    }

    private static boolean checkSHA256(File provingKey, Component parent) throws IOException {
        MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException impossible) {
            throw new RuntimeException(impossible);
        }
        try (InputStream is = new BufferedInputStream(new FileInputStream(provingKey))) {
            ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(parent,"Verifying proving key",is);
            pmis.getProgressMonitor().setMaximum(PROVING_KEY_SIZE);
            pmis.getProgressMonitor().setMillisToPopup(10);
            DigestInputStream dis = new DigestInputStream(pmis, sha256);
            byte [] temp = new byte[0x1 << 13];
            while(dis.read(temp) >= 0);
            byte [] digest = sha256.digest();
            return SHA256.equalsIgnoreCase(DatatypeConverter.printHexBinary(digest));
        }
    }
}
