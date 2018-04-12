#include <LiquidCrystal.h>     //libreria a utilizar para la pantalla lcd

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);    // pines utilizados en arduino para la pantalla lcd
int imprimir=0;                           // variable para la impresion del mensaje
String Mensaje="";                        //variable que obtendra el mensaje

//temperatura
byte PIN_SENSOR = A0;                     //pin donde se conecta el sensor lm35
int dato_serial = 0;                      //variable del serial a comunicacion  
float C;                                  //variable donde se guardara el resultado de la operacion
int temp;                                 //escribira la temperatura


//mili
unsigned long time;                      //variable de tirmpo
unsigned long t = 0;                     //se inicializa t para repetir los mensajes 
int Dt = 100;                            //variable que se inicializa, seran los milisegundos que tardara en repetir 

void setup(){
pinMode(10,OUTPUT);                      //pin que se dirige al led
digitalWrite(10,HIGH);                   //escribe
lcd.begin(16, 2);                        //Inicializa la interfaz a la pantalla LCD y especifica las dimensiones (ancho y alto)

Serial.begin(9600);                      //Establece la velocidad de datos en bits por segundo
//estaes del reloj
Serial.setTimeout(50);                   //establece los milisegundos máximos para esperar datos

}

void loop(){
//reloj
String text = Serial.readString();                       //lee caracteres del buffer serial en una cadena. La función finaliza si se agota el tiempo de espera (ver setTimeout ())
String line1 = text.substring(0, 16);                    //Obtener una subcadena de una cadena
String line2 = text.substring(16, 32);                   //Obtener una subcadena de una cadena

C = (5.0 * analogRead(PIN_SENSOR) * 100.0)/ 1024;       //operacion para determinar la temperatura

//if(text.length() > 0){

 // }

//  lcd.setCursor(0,0);
 // lcd.print(line1);
 // lcd.setCursor(0,1);
 // lcd.print(line2);
 

 if ( Serial.available() > 0){            //Obtiene la cantidad de bytes (caracteres) disponibles para leer desde el puerto serie
   //lectura_dato();
   dato_serial = Serial.read();           //la variable lee los datos de serie entrantes 
   comparacion_dato();              
}
     //

   lcd.setCursor(0,0);                    //nos posicionamos en el 0,0
   lcd.print("C=      Grados");           //imprime el mensaje
   lcd.setCursor(2,0);                    //nos posicionamos en el 2,0
   lcd.print(C);                          //se imprime la temperatura
   temp = C;                              //guardamos C en temp
  //
  //



int cuenta=0;                             //variable para llevar conteo de mensaje
int caracteres=0;                         //caracteres entrantes


while (Serial.available()>0){                             //mientras haya caractere disponibles para leer desde el puerto serie hara los mandara para convertir a ASCII
Mensaje=Mensaje+Decimal_to_ASCII(Serial.read());
//text = text + Decimal_to_ASCII(Serial.read());
cuenta++;                                                 //lleva la cuenta de cada vez que pasa un mensaje en partes

}  
caracteres=Mensaje.length();                             //cuenta los caracteres que tiene el mensaje
delay(10000);                                            //espera un tiempo definido 

if(text.length() > 0){                                   //verifica si text es mayor que cero para poder imprimir
  lcd.setCursor(0,0);                                    //se coloca en la posicion 0,0
  lcd.print("                ");                         //se imprime espacio
  lcd.setCursor(0,1);                                    //se coloca en la posicion 0,0
  lcd.print("                ");                         //se imprime espacio
 }

  lcd.setCursor(0,0);                                    //se coloca en la posicion 0,0
  lcd.print(line1);                                      //se imprime la linea uno de la pantalla
  lcd.setCursor(0,1);                                    //se coloca en la posicion 0,1
  lcd.print(line2);                                      //se imprime la linea dos de la pantalla
   delay(5000);                                          //tiempo de 5000(5 segundos)
 

if(time-t > Dt){                                         //condicion para que se cicle
if (caracteres>16){                                      //condicion que si es mayor a 16 siga para imprimir
  if (Mensaje!=""){                                      //si es diferente de nulo lo va a imprimir
   lcd.clear();                                          //limpia la pantalla lcd
   lcd.print(Mensaje.substring(0,16));                   //imprime la primera linea, primeros 16 caracteres
   lcd.setCursor(0,1);                                   //nos posicionamos en la 0,1
   lcd.print(Mensaje.substring(16,caracteres));          //imprime los degundos 16 caracteres
}
}
else                                                     //de lo contrario realiza lo siguiente
{
//
if (Mensaje!=""){                                        //limpia e imprime
    t = time;                                            
    lcd.clear();
    lcd.print(Mensaje); 
  } 
 //
}
}
delay(10000);                                           //espera un tiempo(10 segundos)
Mensaje="";                                             //saca mensaje 
 lcd.clear();                                           //limpi pantalla
//temperatura
}

void lectura_dato (void ){                             //metodo para leer el serial, cada caractaer
 dato_serial = Serial.read();  
}

void comparacion_dato (void){                          //si es vacion escribe el temp
  if(dato_serial == ' '){ 
      Serial.write(temp);   
      
  
  }
}

 
char Decimal_to_ASCII(int entrada){//metodo que toma lo enviado por llava y lo convierte a ASCII para poder imprimir en pantalla, contiene todos las letras, mayusculas, minisculas y simbolos   
  char salida=' ';
  switch(entrada){
case 32: 
salida=' '; 
break; 
case 33: 
salida='!'; 
break; 
case 34: 
salida='"'; 
break; 
case 35: 
salida='#'; 
break; 
case 36: 
salida='$'; 
break; 
case 37: 
salida='%'; 
break; 
case 38: 
salida='&'; 
break; 
case 39: 
salida=' '; 
break; 
case 40: 
salida='('; 
break; 
case 41: 
salida=')'; 
break; 
case 42: 
salida='*'; 
break; 
case 43: 
salida='+'; 
break; 
case 44: 
salida=','; 
break; 
case 45: 
salida='-'; 
break; 
case 46: 
salida='.'; 
break; 
case 47: 
salida='/'; 
break; 
case 48: 
salida='0'; 
break; 
case 49: 
salida='1'; 
break; 
case 50: 
salida='2'; 
break; 
case 51: 
salida='3'; 
break; 
case 52: 
salida='4'; 
break; 
case 53: 
salida='5'; 
break; 
case 54: 
salida='6'; 
break; 
case 55: 
salida='7'; 
break; 
case 56: 
salida='8'; 
break; 
case 57: 
salida='9'; 
break; 
case 58: 
salida=':'; 
break; 
case 59: 
salida=';'; 
break; 
case 60: 
salida='<'; 
break; 
case 61: 
salida='='; 
break; 
case 62: 
salida='>'; 
break; 
case 63: 
salida='?'; 
break; 
case 64: 
salida='@'; 
break; 
case 65: 
salida='A'; 
break; 
case 66: 
salida='B'; 
break; 
case 67: 
salida='C'; 
break; 
case 68: 
salida='D'; 
break; 
case 69: 
salida='E'; 
break; 
case 70: 
salida='F'; 
break; 
case 71: 
salida='G'; 
break; 
case 72: 
salida='H'; 
break; 
case 73: 
salida='I'; 
break; 
case 74: 
salida='J'; 
break; 
case 75: 
salida='K'; 
break; 
case 76: 
salida='L'; 
break; 
case 77: 
salida='M'; 
break; 
case 78: 
salida='N'; 
break; 
case 79: 
salida='O'; 
break; 
case 80: 
salida='P'; 
break; 
case 81: 
salida='Q'; 
break; 
case 82: 
salida='R'; 
break; 
case 83: 
salida='S'; 
break; 
case 84: 
salida='T'; 
break; 
case 85: 
salida='U'; 
break; 
case 86: 
salida='V'; 
break; 
case 87: 
salida='W'; 
break; 
case 88: 
salida='X'; 
break; 
case 89: 
salida='Y'; 
break; 
case 90: 
salida='Z'; 
break; 
case 91: 
salida='['; 
break; 
case 92: 
salida=' '; 
break; 
case 93: 
salida=']'; 
break; 
case 94: 
salida='^'; 
break; 
case 95: 
salida='_'; 
break; 
case 96: 
salida='`'; 
break; 
case 97: 
salida='a'; 
break; 
case 98: 
salida='b'; 
break; 
case 99: 
salida='c'; 
break; 
case 100: 
salida='d'; 
break; 
case 101: 
salida='e'; 
break; 
case 102: 
salida='f'; 
break; 
case 103: 
salida='g'; 
break; 
case 104: 
salida='h'; 
break; 
case 105: 
salida='i'; 
break; 
case 106: 
salida='j'; 
break; 
case 107: 
salida='k'; 
break; 
case 108: 
salida='l'; 
break; 
case 109: 
salida='m'; 
break; 
case 110: 
salida='n'; 
break; 
case 111: 
salida='o'; 
break; 
case 112: 
salida='p'; 
break; 
case 113: 
salida='q'; 
break; 
case 114: 
salida='r'; 
break; 
case 115: 
salida='s'; 
break; 
case 116: 
salida='t'; 
break; 
case 117: 
salida='u'; 
break; 
case 118: 
salida='v'; 
break; 
case 119: 
salida='w'; 
break; 
case 120: 
salida='x'; 
break; 
case 121: 
salida='y'; 
break; 
case 122: 
salida='z'; 
break; 
case 123: 
salida='{'; 
break; 
case 124: 
salida='|'; 
break; 
case 125: 
salida='}'; 
break; 
case 126: 
salida='~'; 
break; 
  }
  return salida;              //regresa la salida
}

