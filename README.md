# HandGestureRecognition

* 스마트 글러브와 함께 사용되는 제스처 인식 프로그램
* 각 제스처를 인식할 수 있도록 학습 모델과 같이 업로드 되어 있음
* 인식 가능한 제스처
> 1. Eat with the hand (HA)
2. Drink from a glass (GL)
3. Eat some fruit with a fork/chopsticks from a dish (FK), Eat some cut fruit with a fork from a dish (FK), Eat some cut fruit with a chopsticks from a dish (CS)
4. Eat some yogurt with a spoon from a bowl (SP)
5. Drink from a mug (CP)
6. Answer the telephone (PH)
7. Brush the teeth with a toothbrush (TB)
8. Brush hair with a hairbrush (HB)
9. Use a hair dryer (HD)

* 각 환경에 맞게 포트 번호는 소스에서 직접 수정해서 사용해야 함
> `src/kr.re.keti/LifeGesture.java` Line 59의 내용을 윈도우에서는 `COM 1(해당포트번호)`로 리눅스에서는 `/dev/ttyUSB0(해당 디바이스 번호)로 수정한다

* 리눅스에서는 `/dev/ttyUSB0`포트 접근권한때문에 관리자 권한으로 실행한다.
```
$ sudo java -jar HandGestureRecognition.jar
```

실행결과

