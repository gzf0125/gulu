function loginApi(data) {
  return $axios({
    'url': '/employee/login',<!--请求地址 -->
    'method': 'post',
    data
  })
}

function logoutApi(){
  // 发送axios请求
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
    //向/employee/logout地址发送post请求
  })
}
