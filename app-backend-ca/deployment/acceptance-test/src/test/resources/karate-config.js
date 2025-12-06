function fn() {
    var env = karate.env || 'dev';
    karate.log('karate.env is:', env);

    var config = {
        appId: 'my.app.id',
        appSecret: 'my.secret'
    };

    // Set baseUrl based on environment
    if (env === 'dev') {
        config.baseUrl = 'https://dev-api.example.com/api/v1';
    } else if (env === 'stage') {
        config.baseUrl = 'https://stage-api.example.com/api/v1';
    } else if (env === 'prod') {
        config.baseUrl = 'https://api.example.com/api/v1';
    } else {
        config.baseUrl = 'http://localhost:9000/api/v1';
    }

    karate.log('Using baseUrl:', config.baseUrl);
    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    return config;
}
