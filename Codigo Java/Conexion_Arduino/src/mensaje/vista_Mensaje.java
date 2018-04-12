package mensaje;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort; 
import gnu.io.UnsupportedCommOperationException;
import java.awt.event.ActionEvent;
import java.io.IOException;  
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javax.swing.JFrame;


public class vista_Mensaje extends javax.swing.JFrame {

    int caracteres = 32;               //maximo de caracteres a imprimir
    private OutputStream Output = null;      //variable de conexion
    SerialPort serialPort;                   //serial que hara posible detectar y leer, (conexion)     
    private final String PORT_NAME = "COM6"; //puerto a utilizar
    private static final int TIME_OUT = 2000;   
    private static final int DATA_RATE = 9600;  //bits por segundo
    
    //TEMP
    InputStream in = null;
    int temperatura=10;
    Thread timer;
    
    public vista_Mensaje() {
        initComponents();
        ArduinoConnection();//se inicializa la conexion
        letras();           //metodo letras para que empieze en el constructor
        //EnviarDatos();            
    }
    
    public void ArduinoConnection() {  //metodo de conexion a arduino
        CommPortIdentifier portId = null;      //identificador de puerto
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();  //obtiene el identificador

        while (portEnum.hasMoreElements()) {        //si tiene mas elementos ejecuta lo siguiente
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

        if (PORT_NAME.equals(currPortId.getName())) {  //si el puerto es el mismo conecta
        portId = currPortId;
        break;
            }
        }

        if (portId == null) {       //si no esta manda error y saca del programa
        System.exit(ERROR);
        return;
        }

        try {
            
            //parametros necesarios para la conexion serial 
        serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
        serialPort.setSerialPortParams(DATA_RATE,
        SerialPort.DATABITS_8,
        SerialPort.STOPBITS_1,
        SerialPort.PARITY_NONE);
        Output = serialPort.getOutputStream();

        } catch (Exception e) {

        System.exit(ERROR);
        }
    }
    
    private void EnviarDatos(String data) {
        try {
        Output.write(data.getBytes());  //envia datos de byte por baty que toma arduino con la funcion 
        } catch (IOException e) {       //si no los obtiene manda un error
        System.exit(ERROR);
        }
    }
    
    public void letras() {
        caracteres = 32 - txtMensaje.getText().length(); //Indica la cantidad de caracteres
        //disponibles. En el LCD solo se permite imprimir 32 caracteres.

        if (caracteres <= 0) { //Si la cantidad de caracteres se ha agotado...
        labelCaracter.setText("Caracteres disponibles: 0"); //Se imprime que la cantidad de caracteres disponibles es 0
        String cadena = ""; //Se declara la variable que guardará el mensaje a enviar
        cadena = txtMensaje.getText(); //Se asigna el texto del TextField a la variable cadena
        cadena = cadena.substring(0, 32); //Se evita que por alguna razón la variable contenga
        //más de 32 caracteres, utilizando el substring que crea un string a partir de uno mayor.
        txtMensaje.setText(cadena); //se regresa la cadena con 32 caracteres al TextField
        } else {
        //Si la cantidad de caracteres disponibles es ayor a 0 solamente se imprimirá la cantidad de caracteres disponibles
        labelCaracter.setText("Caracteres disponibles: " + (caracteres));
        }
    }

    public void hora(){
        Thread thread = new Thread() {
            @Override
            public void run() {                
                try {
                    Thread.sleep(100);  //hilo que hace espera
                } catch (Exception e) {
                }
                
                PrintWriter output;
                try { 
                    //hacemos conexion mediante el puerto
                    output = new PrintWriter(serialPort.getOutputStream());
                    while (true) {
                        //se da el formato de hora desde java para mandar a arduino
                        output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMM dd, yyyy").format(new Date()));
                        output.flush();
                        try {
                            Thread.sleep(100); //hilo que hara espera
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(vista_Mensaje.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
    }
    
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMensaje = new javax.swing.JTextField();
        labelCaracter = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnVer = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnHora = new javax.swing.JButton();
        btnTemperatura = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });
        txtMensaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMensajeKeyReleased(evt);
            }
        });

        jLabel2.setText("Escribe un Mensaje");

        btnVer.setText("Ver Mensaje");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar Mensaje");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnHora.setText("Ver Hora");
        btnHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoraActionPerformed(evt);
            }
        });

        btnTemperatura.setText("Ver Temperatura");
        btnTemperatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemperaturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnVer)
                                    .addComponent(btnHora))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTemperatura)
                                    .addComponent(btnLimpiar))))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVer)
                    .addComponent(btnLimpiar))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHora)
                    .addComponent(btnTemperatura))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMensajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMensajeKeyReleased
        letras();         //llama el metodo letras para contar cuantos caracteres de han escrito en el textfield
    }//GEN-LAST:event_txtMensajeKeyReleased

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        EnviarDatos(txtMensaje.getText()); //envia los datos que se ingresaron en el textfield
        txtMensaje.setText("");            //deja el campo en blanco
                   
        letras();                          //llama al metodo letras
        hora();                            //lama al metodo horas
           Thread thread = new Thread() {
            @Override
            public void run() {                
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }                                                                   
                    while (true) {                          
                                               
                        try { 
                            Thread.sleep(5000); 
                        } catch (Exception e) {
                        }                      
                    }                
            }
        };
        thread.start();   
        
       
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtMensaje.setText("");  //limpia el campo y llama metodo
        letras();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        EnviarDatos(txtMensaje.getText());   //envia mensaje 
        txtMensaje.setText("");        //deja el campo en blanci
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void btnHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoraActionPerformed
        hora();                              //llama la hora 
        EnviarDatos(txtMensaje.getText());   //envia informacion 
        txtMensaje.setText("");              //limpia textfield
        letras();                            //llama al metodo letras para el mensaje
    }//GEN-LAST:event_btnHoraActionPerformed

    public void temperatura(){        		

        serialPort.close();   //cierra conexion 
        try {             
            Output.close();            //cierra conexion
        } catch (IOException ex) {
            Logger.getLogger(vista_Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
            //this.dispose();
//            Ventana2 v= new Ventana2();
//            v.setVisible(true);
//            v.setSize(400,300);
    }
        
        
    
    private void btnTemperaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemperaturaActionPerformed
        temperatura();      //llama a temperatura     
    }//GEN-LAST:event_btnTemperaturaActionPerformed
    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                
                new vista_Mensaje().setVisible(true);
                        
                        
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHora;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnTemperatura;
    private javax.swing.JButton btnVer;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelCaracter;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
