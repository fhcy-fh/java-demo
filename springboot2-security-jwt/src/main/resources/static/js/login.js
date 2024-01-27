function login(data) {

// RSA公钥
    const publicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjce7I+byDS2t/IQcqhtMEmaYWJe8iFYPCMP24AJ5EhUxg1yT3+IUWZU15MdOtxAzurLd7iKwXtSE7ZsrqH1a1Flcd2uClliTRxqPb2gc7Y13nUaRGvdPlUJHkoddDjHYUBhPhMOumqK65LG3CDwnuo7iO4F121Qr9gbT7SXMukF9/xpW2K+nP5KJcCySjFhlHybuVfuFCi7ZwJJel9WR5gWeEzekGI8xYVGW0Y+RhghV8nsBpsPvRwZYqUp3HJylC1lXZ3jAjd8vNp6vAMctxB2DcZfUBBz9EjiedAeu+T3emA58Tzq6cp4FV6w+gPRKRwaFa3aCzprs17wiVapZFQIDAQAB'; // 这里只展示了部分内容

    const encryptor = new JSEncrypt()
    encryptor.setPublicKey(publicKey) // 设置公钥
    data.password = encryptor.encrypt(data.password) // 对数据进行加密

    return axios({
        url: '/mylogin',
        method: 'post',
        data: data,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    });
}
