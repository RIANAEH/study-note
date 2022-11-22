# JVM

JVM은 크게 4가지로 구성됩니다. Garbage Collector, Execution Engine, Class Loader, Runtime Data Area가 있습니다. 

![IMG_6347](https://user-images.githubusercontent.com/45311765/202437065-76d1018c-f680-4bc1-8121-70767facbfe9.jpg)


Class Loader는 컴파일러에 의해 생성된 클래스 파일을 런타임시에 메모리로 로드하고 링킹을 수행합니다. Execution Engine은 Runtime Data Area에 배치된 바이트 코드들을 명령어 단위로 읽어서 실행합니다. 이때 인터프리터를 이용하는데, 일정한 기준이 넘어가면 JIT 컴파일러로 바이트 코드를 어셈블러와 같은 네이티브 코드로 바꿔 실행합니다. Garbage Collector는 주기적으로 힙 메모리 영역에 있는 객체들 중에서 참조되지 않는 객체들을 탐색한 후 제거합니다. 

Runtime Data Area는 JVM의 메모리 영역으로 자바 어플리케이션을 실행할 때 사용하는 데이터들을 적재하는 영역입니다. Method Area, Heap Area, Stack Area, PC Register, Native Method Stack의 5가지 영역으로 구분됩니다.


## Execution Engine

Execution Engine은 Runtime

## 참고 자료
- 2019년 1학기 김영종 교수님의 네트워크 프로그래밍 강의
