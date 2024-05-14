import axios from "axios";
import User from './User';
import {useEffect, useState} from "react";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";

const Users = ({func, showAdd, searchText}) => {
    func('Users');
    showAdd(true);

    const history = useHistory();
    const [users, setUsers] = useState([]);

    const reloadCallback = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.get(`${APP_API_ENDPOINT_URL}/users`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                let filter_data = response.data.filter(value => Object.values(value).find(item => String(item).toLowerCase().includes(searchText.toLowerCase())) !== undefined)
                setUsers(filter_data)
            });
    }

    useEffect(() => {
        reloadCallback();
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
                    <User key={user.id} user={user} reloadCallback={reloadCallback}/>
                ))
                : console.log("No users in list")
            }

        </>
    );
};

export default Users;