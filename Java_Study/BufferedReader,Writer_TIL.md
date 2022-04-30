# 220212_TIL

### java

#### java에서 튜플 구현

java는 파이썬과 같이 튜플쌍을 이루는 빌트인 함수가 없기 때문에 Pair 클래스를 생성하여 튜플을 구현한다.

```java
class Pair {
    int first, second;
    // Pair는 생성자임
    Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}
```



#### BufferedReader / BufferedWriter

**※ Buffer**: 데이터를 한 곳에서 다른 한 곳으로 전송하는 동안 일시적으로 그 데이터를 보관하는 메모리 영역.
입/출력, 네트워크 관련 기능에 활용됨(입출력 속도를 향상 시키기 위해 사용).
순서대로 입력/출력/전달되어야 하므로, FIFO 방식의 자료구조인 큐(Queue)를 활용함.

※ 버퍼링: 버퍼를 활용하는 방식 또는 버퍼를 채우는 동작을 말함 

![자바 bufferedreader 2022-02-13 100906](https://user-images.githubusercontent.com/93081720/153756554-33ff85e7-754c-4e71-8df3-1a6baa22ed8b.png)

원래 하드디스크뿐만 아니라, 키보드나 모니터와 같은 외부 장치의 데이터 입출력 속도는 시간이 걸림. 버퍼링 없이 키보드를 누를 때마다 눌린 문자열의 정보를 목적지로 바로 이동시키는 것보다 **중간에 메모리 버퍼를 둬서 데이터를 한 군데에 묶어놓았다가 한번에 전송하는 것이 보다 효율적이고 빠름**

흙 더미를 옮기는데, 삽질 한번 할때마다 이동해서 흙을 옮기는 것보다 수레에 흙을 모아뒀다가 한번에 옮기는 것이 더 효율적인 것과 비슷함

- 장점: Scanner에 비해 데이터를 빠르게 입출력 가능하게 함
- 단점: enter(엔터)만 경계로 인식하고 받은 데이터가 `String`으로 고정되기 때문에 필요에 따라 데이터를 추가 가공해줘야하는 작업이 필요함 → Line(줄) 단위로 읽어오기 때문에 공백 단위로 나눠 주는 등



BufferedReader를 사용할 때는 반드시 try / catch 구문 또는 throws를 통해 IOException에 대한 예외처리를 해줘야한다.

※ `IOException`: **I**nput/**O**utput에 대한 Exception(예외)처리

※ `throw`: 예외를 발생시키는 명령. throw 뒤에는 예외 정보를 가지고 있는 예외 클래스가 위치한다. 자바 가상 머신은 이 클래스를 기준으로 어떤 catch 구문을 실행할 것인지를 결정한다. 또 실행되는 catch 구문에서는 예외 클래스를 통해서 예외 상황의 원인에 대한 다양한 정보를 얻을 수 있다. 이 정보를 바탕으로 문제를 해결하게 된다.

BufferedWriter는 System.out.println()과 같이 출력과 동시에 개행을 해주지 않기 때문에 따로 개행문자를 넣어줘야함

 - `bw.write(요소);` 혹은 `bw.newline();`은 버퍼에 요소를 쓰는 것이지 버퍼에 있는 것을 출력하는 의미가 아님. 버퍼에 있는 것을 출력하는 것은 `bw.flush();`임



- 예시)

```java
import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        try {
            // 콘솔 입력 시
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // 파일 입력 시
            FileReader fr = new FileReader("BufferedReaderTest.txt");
            BufferedReader fbr = new BufferedReader(fr);

            // String → int 형변환
            String s1 = br.readLine();
            int num = Integer.parseInt(br.readLine());

            // 입력 종료 후 버퍼 닫기
            br.close();
            
            // 공백 구분한 데이터 가공
            // 첫번째 방법: StringTokenizer에 문자열 입력
            StringTokenizer st = new StringTokenizer(s1); // StringTokenizer인자값에 입력 문자열 넣음
            int a = Integer.parseInt(st.nextToken()); // 첫번째 호출
            int b = Integer.parseInt(st.nextToken()); // 두번째 호출

            // 두번째 방법: 공백마다 끊어서 배열에 저장
            String[] arr = s1.split(" "); // 공백마다 데이터 끊어서 배열에 넣음

            // System.out.println을 활용한 한줄씩 출력(BufferedWriter보다 느림)
            String line = "";
            for (int i=0; (line = fbr.readLine()) != null; i++) {
                System.out.println(line);
            }

            // BufferedWriter를 활용한 출력
            // 할당된 버퍼에 값 넣어주기
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));   
            String s2 = "abcdefg";   // 출력할 문자열
            bw.write(s2+"\n");   // 버퍼에 값 넣기
            bw.newLine(); // bw.write("\n")과 같은 의미

            // 파일 출력
            BufferedWriter bw = new BufferedWriter(new FileWriter("BufferedWriterTest.txt"));
            bw.write("hello java\n");
            bw.write("I am writting\n");
            bw.flush();   // 버퍼에 있는 데이터를 모두 출력시킴
            bw.close();   // 스트림을 닫음
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
```



- 백준) 빠른 A+B (15552번) - throws로 IOException 처리함

```java
import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        for (int i=0; i<T; i++) {
            String nums = br.readLine();
            String[] arr = nums.split(" ");
            int A = Integer.parseInt(arr[0]);
            int B = Integer.parseInt(arr[1]);
            int res = A+B;
            bw.write(res+"\n");
            // bw.newLine();
        }
        bw.flush(); // bw.flush()
        br.close();
        bw.close();
    }
}

5
1 1
12 34
5 500
40 60
1000 1000
```

