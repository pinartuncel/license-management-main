import axios from "axios";
import User from './User';
import {useState, useEffect} from "react";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory, useParams} from "react-router-dom";

const UsersByCompany = (props) => {
    props.func('Users in company');
    props.showAdd(false);

    const {id} = useParams();

    const history = useHistory();
    const [users, setUsers] = useState([]);

    useEffect(() => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.get(`${APP_API_ENDPOINT_URL}/companies/${id}/users`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                setUsers(response.data);
            });
    }, []);


    return (
        <>
            <div className='user flex justify-items-start py-2 border-b-2'>
                <span className='tbl-row tbl-head'>Company</span>
                <span className='tbl-row tbl-head'>Username</span>
                <span className='tbl-row tbl-head'>First name</span>
                <span className='tbl-row tbl-head'>Last name</span>
                <span className='tbl-row tbl-head'>Email</span>
                <span className='tbl-row tbl-head'>Phone</span>
                <span className='tbl-row tbl-head'>Mobile</span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='clearfix'></span>
            </div>
            {users ?
                users.map((user) => (
                    <User key={user.id} user={user}/>
                ))
                : console.log("No users in list")
            }

        </>
    );
};

export default UsersByCompany;