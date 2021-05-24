char command;
String string;
boolean ledon = false;
#define led 5
#define led2 6

void setup() {
  Serial.begin(9600);
  pinMode(led, OUTPUT);
  pinMode(led2, OUTPUT);

}

void loop() {
  while(Serial.available() > 0)
    {
  command = ((byte)Serial.read());
  string += command;
  
  Serial.print(command);

  if(string =="1")
  {
    ledOn();
  }
  if(string =="2")
  {
    ledOn2();
  }}
  // put your main code here, to run repeatedly:

}

void ledOn()
   {
      analogWrite(led, 255);
      //digitalWrite(led,5);
      delay(1000);
    }

void ledOn2()
   {
      analogWrite(led2, 255);
      
      delay(1000);
    }
