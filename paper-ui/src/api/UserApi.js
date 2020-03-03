import axios from 'axios'

const AUTH_URL = `${process.env.REACT_APP_URL}/users/sessions`;

class UserApi {
    static auth(userName, password) {
        let payload = {
            userName: userName,
            password: password
        };

        return axios.post(AUTH_URL, payload)
            .then((resp) => {
                console.log(resp);
                localStorage.setItem("token", resp.data.token.value);
                return resp.status;
            })
            .catch((error) => {
                console.log(error);
            });
    }
}

export default UserApi

