const AUTH_URL = `${process.env.REACT_APP_URL}/users/sessions`;

class UserApi {
    static auth(userName, password) {
        let payload = {
            userName: userName,
            password: password
        };

        return fetch(AUTH_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(payload)
        })
            .then(resp => {
                return resp.json();
            })
            .then(resp => {
                if (resp.token !== undefined)
                    localStorage.setItem("token", resp.token.value);
                else
                    throw resp;
            });
    }
}

export default UserApi

