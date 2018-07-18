package com.api.automation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class CertLoader {

    @PostConstruct
    public void loadCertificatesToKeyStore()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        try {
            char sep = File.separatorChar;
            File dir = new File(System.getProperty("java.home") + sep + "lib" + sep + "security");
            File file = new File(dir, "cacerts");
            InputStream caCert = new FileInputStream(file);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(caCert, "changeit".toCharArray());

            URL filePath = this.getClass().getClassLoader().getResource("certificates");
            File certificateDirectoryPath = null;
            if (null != filePath) {
                certificateDirectoryPath = new File(filePath.getFile());
                if (certificateDirectoryPath.exists() && certificateDirectoryPath.isDirectory()) {

                    // list out certificates
                    File[] certificates = certificateDirectoryPath.listFiles();
                    for (File certificate : certificates) {
                        String fileName = certificate.getName();
                        String certificatename = fileName.substring(0, fileName.indexOf("."));
                        if (!keystore.containsAlias(certificatename)) {
                            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(certificate));
                            CertificateFactory cf = CertificateFactory.getInstance("X.509");
                            while (bis.available() > 0) {
                                Certificate cert = cf.generateCertificate(bis);
                                keystore.setCertificateEntry(certificatename, cert);
                            }

                            bis.close();

                            OutputStream out = new FileOutputStream(file);
                            keystore.store(out, "changeit".toCharArray());
                            out.close();

                        }

                    }

                }

            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
