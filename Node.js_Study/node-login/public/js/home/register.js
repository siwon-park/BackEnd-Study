const id = document.querySelector('#id');
const name = document.querySelector('#name');
const pw = document.querySelector('#password');
const pw2 = document.querySelector('#password2');
const signUpBtn = document.querySelector("#button");

signUpBtn.addEventListener("click", signUp);

function signUp() {
  if (!id.value) { return alert("아이디를 입력해주십시오")}
  if (pw.value !== pw2.value) {
    return alert("비밀번호가 불일치합니다")
  }

  const req = {
    id: id.value,
    name: name.value,
    pw: pw.value,
  }

  fetch("/signUp", {
    method: "post",
    headers: {
      "Content-Type": "application/json", // json 타입으로 받겠다고 명시
    },
    body: JSON.stringify(req)
  })
    .then((res) => res.json()) // res.json의 반환값은 Promise 객체임(아래의 res는 Promise 객체인 res를 말함)
    .then((res) => {
      if (res.success) {
        location.href = "/login";
      } else {
        alert(res.msg);
      }
    })
    .catch((err) => {
      console.error("회원가입 중 에러 발생");
    })
}