function api(baseUrl) {
    karate.log('baseUrl -> ',baseUrl);
    if (!baseUrl) {
        throw 'baseUrl parameter is required';
    }

    return {
        urls: {
            users: baseUrl + '/users',
            userById: baseUrl + '/users',
            userProfile: baseUrl + '/users/profile'
        },

        request: {
            createUser: {
                name: 'mar',
                email: 'mar@gmail.com',
                createdAt: '2025-12-12'
            },
        },

        schemas: {
            user: {
                id: '#number',
                name: '#string',
                email: '#string',
                password: '#string',
                createdAt: '#string'
            }
        },
        userExpected: {
            id: '#number',
            name: '#string',
            email: '#regex ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$',
            createdAt: '#string'
        }
    };
}
