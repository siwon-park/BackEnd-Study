# 41_빌드된 jar 안에 변경된 클래스 반영 확인

> jar 안에 변경된 클래스가 반영됐는지 확인하는 방법들

## 1. 파일 존재 및 수정 시각 확인

```bash
unzip -l app.jar | grep MyClass.class
```

출력 예시

```
  3421  2026-08-22 13:05   com/example/MyClass.class
```

- jar 내 대상 클래스의 타임스탬프를 확인하여 최근 빌드에 반영된 것인지 대략적으로 확인이 가능하다
- 다만 jar 빌드 시점에 타임스탬프가 리셋되는 경우도 있어서 "언제 빌드되었나"보다는 "그 클래스가 jar에 포함되어 있는가"를 보는 용도에 더 적절

<br>

## 2. 내용 비교

### 1) 바이트 코드 내용 비교 (diff)

수정한 코드가 빌드된 jar 안에 정확하게 반영되었는지 확인하려면 jar에서 해당 클래스를 추출하여 diff를 해보는 것이 가장 좋다.

- 단점으로는 서버에서 파일을 빼와야 한다는 번거로움이 있다.

```bash
# jar에서 해당 클래스만 추출 (-p: stdout으로 해당 파일 내용을 뽑아내는 옵션)
unzip -p app.jar com/example/MyClass.class > deployed.class

# 로컬 빌드 결과물과 비교(diff)
diff deployed.class /path/local/build/classes/com/example/MyClass.class
```

<br>

### 2) 해시값 비교(md5sum)

md5sum의 경우 파일 내용 전체를 읽어서 고정 길이의 해시값으로 변환한다. `md5sum [파일1] [파일2]`를 해도 되지만, 이렇게 할 경우 diff와 동일하게 파일을 빼와야하는 번거로움이 생기기 때문에 md5sum 결과 해시값만 추출하여 비교하는 방식으로 사용하면 된다.

```bash
unzip -p app.jar com/example/MyClass.class | md5sum # md5 해시값 출력
```

출력되는 해시값을 로컬에서 동일하게 대상 클래스 파일에 md5sum을 하여 결과 해시값을 비교하여 같으면 동일하다면, 수정 사항이 배포 파일에 잘 반영되었다고 볼 수 있다.