# [Redis](https://redis.io/)가 먼가여?

Redis는 remote dictionary server로 딕셔너리 형태로 데이터를 저장하는 in-memory 데이터베이스를 의미합니다. 

Redis는 in-memory를 사용하는 데이터베이스이기 때문에 접근 속도가 MySQL과 같은 RDBMS 보다 훨씬 빠릅니다. 

> `CPU Register -> CPU Cache -> Main Memory -> Storage(SSD, HDD)` 순으로 접근 속도가 느려지고 가격이 저렵해진다.

## 언제 사용해요?

이러한 특성 덕분에 레디스는 고속처리 데이터베이스, 캐시, 스트리밍 엔진, 메시지 브로커, 분산락 등에 사용될 수 있습니다. 

## 레디스의 단점은?

Java의 HashMap과 같은 자료구조를 활용해 데이터를 저장한다면 서버가 여러대인 경우 **데이터 정합성(consistency) 문제**가 발생할 수 있으며, 멀티 스레드 환경에서는 **경쟁 상태(race condition) 문제**가 발생할 수 있습니다. 

## 레디스는 경쟁 상태를 해결하기 위해..

멀티 스레드가 아닌 싱글 스레드를 활용하며, 임계 영역에 대한 원자적인 동기화를 제공합니다. 또한 서로 다른 트랜잭션의 읽기와 쓰기에 대한 동기화를 제공합니다. 

자바 진영의 레디스 클라이언트로는 Jedis, Lettuce, Redisson 등이 있습니다. 
