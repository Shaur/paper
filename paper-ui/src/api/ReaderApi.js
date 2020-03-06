class ReaderApi {
    static pages(seriesId, numberId) {
        return fetch(`${process.env.REACT_APP_URL}/reader/read/${seriesId}/${numberId}`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            }
        })
            .then(response => response.json())
            .catch(() => {
                this.props.history.push('/login');
            })
    }
}

export default ReaderApi