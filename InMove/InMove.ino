#include <Servo.h>
#define INST_ARRAY_LEN 6
#define FINGER_CLOSED 140
#define FINGER_OPEN 0
const int PINS[5] = {2,12,8,10,4};

Servo fingers[5];
bool positions[5];
char instruction[INST_ARRAY_LEN];
int instIndex = 0;

void assignPins(){
  for(int i = 0; i < 5; i++){
    fingers[i].attach(PINS[i]);
  }
}

void detachPins(){
  for(int i = 0; i < 5; i++){
    fingers[i].detach();
  }
}

void reset(){
  for(int i = 0; i < 5; i++){
    fingers[i].write(0);
    positions[i] = false;
  }
}

void setup(){
  Serial.begin(9600);
  assignPins();
  reset();
}

void loop() {
  if (Serial.available() > 0){
    char nextChar = Serial.read();
    //if char is new line last instruction complete, process instruction
    if(nextChar == '\n'){
      if(instIndex > 0) {
        instruction[instIndex] = nextChar;
        processInstruction(instruction);
        instIndex = 0;
      }
    }
    //add to instruction string
    else {
      if(instIndex >= INST_ARRAY_LEN)  
        Serial.println("ERROR - loop() instruction parser: Instruction index out of bounds.");
      else{
        instruction[instIndex] = nextChar;
        instIndex++;
      }
    }
  }
}

int processInstruction(char* input){
  if(input[0] == 'k'){
    detachPins();
  } else if(input[0] == 'w'){
    assignPins();
    reset();
  } else if(input[0] == 'r'){
    reset();
  } else {
    for(int i = 0; i < 5; i++){
      Serial.println(input[i] == '1');
      if(input[i] == '1'){
        fingers[i].write(FINGER_CLOSED);
      } else {
        fingers[i].write(FINGER_OPEN);
      }
    }
    Serial.flush();
  }
}
