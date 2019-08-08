#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN 9                 //Pin 9 para el reset del RC522
#define SS_PIN 10                 //Pin 10 para el SS (SDA) del RC522
MFRC522 mfrc522(SS_PIN, RST_PIN); ///Creamos el objeto para el RC522

void setup()
{
    Serial.begin(9600); //Iniciamos La comunicacion serial
    SPI.begin();        //Iniciamos el Bus SPI
    mfrc522.PCD_Init(); // Iniciamos el MFRC522
}

byte ActualUID[4]; //almacenará el código del Tag leído
//códigos de los objetos
byte Objeto1[4] = {0x31, 0xA5, 0xAA, 0x59};//Opcion A
byte Objeto2[4] = {0x45, 0x67, 0xB3, 0xE2};//Opcion B
byte Objeto3[4] = {0x83, 0x7F, 0xAA, 0x59};//Opcion C
byte Objeto4[4] = {0x44, 0x5C, 0x8C, 0x08};//Opcion D
byte Objeto5[4] = {0x44, 0x6C, 0xF1, 0x08};//Saguamanchica
byte Objeto6[4] = {0xA4, 0x20, 0x85, 0x59};//Bachue
byte Objeto7[4] = {0xBA, 0x14, 0xDE, 0x1B};//Hunzahua
byte Objeto8[4] = {0xB9, 0x78, 0xAB, 0x59};//Goranchacha
byte Objeto9[4] = {0x54, 0x10, 0x97, 0x08};//Nemequene
byte Objeto10[4] = {0x44, 0xB3, 0x2F, 0x08};//Bochica

void loop()
{
    // Revisamos si hay nuevas tarjetas  presentes
    if (mfrc522.PICC_IsNewCardPresent())
    {
        //Seleccionamos una tarjeta
        if (mfrc522.PICC_ReadCardSerial())
        {

            //capturamos el UID de la tarjeta
            for (byte i = 0; i < mfrc522.uid.size; i++)
            {
                ActualUID[i] = mfrc522.uid.uidByte[i];
            }

            //comparamos los UID para determinar cual objeto representa
            if (compareArray(ActualUID, Objeto1))
            {
                Serial.println("1");
            }
            else
            {
                if (compareArray(ActualUID, Objeto2))
                {
                    Serial.println("2");
                }
                else
                {
                    if (compareArray(ActualUID, Objeto3))
                    {
                        Serial.println("3");
                    }
                    else
                    {
                        if (compareArray(ActualUID, Objeto4))
                        {
                            Serial.println("4");
                        }
                        else
                        {
                            if (compareArray(ActualUID, Objeto5))
                            {
                                Serial.println("5");
                            }
                            else
                            {
                                if (compareArray(ActualUID, Objeto6))
                                {
                                    Serial.println("6");
                                }
                                else
                                {
                                    if (compareArray(ActualUID, Objeto7))
                                    {
                                        Serial.println("7");
                                    }
                                    else
                                    {
                                        if (compareArray(ActualUID, Objeto8))
                                        {
                                            Serial.println("8");
                                        }
                                        else
                                        {
                                            if (compareArray(ActualUID, Objeto9))
                                            {
                                                Serial.println("9");
                                            }
                                            else
                                            {
                                                if (compareArray(ActualUID, Objeto10))
                                                {
                                                    Serial.println("10");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Terminamos la lectura de la tarjeta tarjeta  actual
            mfrc522.PICC_HaltA();
        }
    }
}

//Función para comparar dos vectores
boolean compareArray(byte array1[], byte array2[])
{
    if (array1[0] != array2[0])
        return (false);
    if (array1[1] != array2[1])
        return (false);
    if (array1[2] != array2[2])
        return (false);
    if (array1[3] != array2[3])
        return (false);
    return (true);
}
