#include <Servo.h>

#define INST_ARRAY_LEN          20

Servo thumb;
Servo pinky;
Servo wrist;
Servo middle;
Servo ring;
Servo index;

Servo fingers[5];
int positions[5];
//serial instructions variable
char instruction[INST_ARRAY_LEN];
int instIndex = 0;

void processInstruction(char *instruction);

void setup() {
  fingers[0] = thumb;
  fingers[1] = index;
  fingers[2] = middle;
  fingers[3] = ring;
  fingers[4] = pinky;
  Serial.begin(9600); 
  for(int i = 0; i < 5; i++){
    fingers[i].write(0);
    positions[i] = 0;
  }
  fingers[0].attach(2); //thumb
  fingers[4].attach(4); //pinky
  // wrist.attach(6); //wrist
  fingers[2].attach(8);//middle
  fingers[3].attach(10);//ring
  fingers[1].attach(12);//index
  //wrist.write(60); // default for wrist is 60 
}

void loop() {
  if (Serial.available() > 0)
  {
    char nextChar = Serial.read();
    //if char is new line last instruction complete, process instruction
    if(nextChar == '\n'){
      if(instIndex > 0) {
        instruction[instIndex] = nextChar;
        //send instruction for processing
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
    
    /*String input = Serial.readString();
    if(input[0] == 'm'){
      int motor = input[2]-48;
      int newPosition = input.substring(4).toInt();
      if(newPosition != positions[motor]){
          positions[motor] = newPosition;
          fingers[motor].write(positions[motor]);
      }
      /*int steps = input.substring(4).toInt();
      if(steps < 0 && positions[motor]+steps >= 0){
          positions[motor] += steps;
          fingers[motor].write(positions[motor]);
      } else if (steps > 0 && positions[motor]+steps <= 140) {
          positions[motor] += steps;
          fingers[motor].write(positions[motor]);
      }*/
      /*Serial.println(positions[motor]);
    } else if (input[0] == 'i'){
      Serial.println("I'm an actobitcs robot arm,Commands:>Motor move; m/[motor_number {0..4}]/[steps]>Get info; i\n");
    } else if (input[0] == 'h'){
      goHome();
    } else if (input[0] == 'd'){
      demo();
    }*/
  }
}

void processInstruction(char *input){
  //check first byte
  switch(input[0]){
    case 'i':
      Serial.println("I'm an actobitcs robot arm,Commands:>Motor move; m/[motor_number {0..4}]/[steps]>Get info; i\n");
      break;
    case 'm':
      {
        int motor = input[2]-48;
        int newPosition = atol(input+4);
        if(newPosition != positions[motor]){
            positions[motor] = newPosition;
            fingers[motor].write(positions[motor]);
        }
        /*int steps = input.substring(4).toInt();
        if(steps < 0 && positions[motor]+steps >= 0){
            positions[motor] += steps;
            fingers[motor].write(positions[motor]);
        } else if (steps > 0 && positions[motor]+steps <= 140) {
            positions[motor] += steps;
            fingers[motor].write(positions[motor]);
        }*/
        Serial.println(newPosition);
      }
      break;
    case 'h':
      goHome();
      break;
    case 'd':
      demo();
      break;
    default:
      Serial.println("Error: invalid command");
  }
}

void goHome(){
  for(int i = 0; i < 5; i++){
    fingers[i].write(0);
    positions[i] = 0;
  }
}

void demo () {
  goHome();
  for(int i = 0; i < 5; i++){
    fingers[i].write(60);
    delay(100);
  }
  for(int j = 0; j < 5; j++){
    fingers[j].write(0);
    delay(100);
  }
  for(int k = 0; k < 5; k++){
    fingers[k].write(140);
    delay(100);
  }
  for(int l = 0; l < 5; l++){
    fingers[l].write(0);
    delay(100);
  }
}
