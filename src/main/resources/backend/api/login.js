function loginApi(data) {
  return $axios({
    'url': '/employee/login',<!--请求地址 -->
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}
