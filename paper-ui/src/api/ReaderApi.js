class ReaderApi {
    static pages(seriesId, numberId) {
        return fetch(`${process.env.REACT_APP_URL}/reader/read/${seriesId}/${numberId}`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            }
        })
            // .then(response => response.json())
            .then(response => {
                if (response.status === 200)
                    return response.json();
                else
                    throw response.json().then;
            })
    }
}

export default ReaderApi