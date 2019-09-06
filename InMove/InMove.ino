#include <Servo.h>
#define INST_ARRAY_LEN 20
#define CHAR_SUBTRACTION 48
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
    int index = input[0] - CHAR_SUBTRACTION;
    bool state = bool(input[1] - CHAR_SUBTRACTION);
    if (state != positions[index]){
      if(state){
        fingers[index].write(FINGER_CLOSED);
      } else {
        fingers[index].write(FINGER_OPEN);
      }
      positions[index] = state;
    }
  }
}
