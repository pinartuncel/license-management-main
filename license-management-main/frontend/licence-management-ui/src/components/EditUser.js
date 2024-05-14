import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory, useParams} from "react-router-dom";
import ButtonTxt from "./ButtonTxt";
import axios from "axios";
import {useEffect, useState} from "react";

const EditUser = (props) => {
    props.func('Edit User');
    props.showAdd(false);

    const {id} = useParams();

    const history = useHistory();
    const [user, setUser] = useState([]);
    const [listIndex, setListIndex] = useState(0);
    const [companies, setCompanies] = useState([]);

    const [username, setUsername] = useState('');
    const [isAdmin, setIsAdmin] = useState(false);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [mobile, setMobile] = useState('');

    useEffect(() => {
        let actualuser = JSON.parse(localStorage.getItem('user'));
        if (actualuser === null) {
            history.push(routes.login);
            return;
        }
        let jwt = actualuser.jwt || '';
        axios.get(`${APP_API_ENDPOINT_URL}/companies`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                setCompanies(response.data);
            });
        axios.get(`${APP_API_ENDPOINT_URL}/users/${id}`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                setUser(response.data);
            });
    }, []);

    useEffect(() => {
        setUsername(user.username);
        setIsAdmin(user.isAdmin);
        setFirstName(user.firstName);
        setLastName(user.lastName);
        setEmail(user.email);
        setPhone(user.phone);
        setMobile(user.mobile);
    }, [user]);


    const onSave = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';

        const updatedUser = {
            id: null,
            username: username,
            isAdmin: isAdmin,
            firstName: firstName,
            lastName: lastName,
            email: email,
            phone: phone,
            mobile: mobile,
            jwt: null,
            company: companies[listIndex]
        };

        axios.put(`${APP_API_ENDPOINT_URL}/users/${id}`, updatedUser, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                history.push(routes.users);
            });
    }
    const onCancel = () => {
        history.push(routes.users);
    }

    return (
        <>
            <div className="grid grid-cols-2 p-2">

                <div className="m-8">
                    <h3 className="">Company</h3>
                    <select onChange={e => setListIndex(e.target.value)}>
                        {companies && user && companies.map((item, i) => <option
                            selected={companies && item.id === user.company.id}
                            value={item.id} key={item.id}>{item.name}</option>)}
                    </select>
                </div>

                <div className="m-4">
                    <h3 className="">Username</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' value={username} onChange={(e) => setUsername(e.target.value)}/>
                </div>
            </div>
            <div className="grid grid-cols-2 p-10">

                <div className="m-8">
                    <h3 className="">First Name</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' value={firstName} onChange={e => setFirstName(e.target.value)}/>
                </div>

                <div className="m-8">
                    <h3 className="">Last Name</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' value={lastName} onChange={e => setLastName(e.target.value)}/>
                </div>

                <div className="m-8">
                    <h3 className="">Email</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' value={email} onChange={e => setEmail(e.target.value)}/>
                </div>

                <div className="m-8">
                    <h3 className="">Phone</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' value={phone} onChange={e => setPhone(e.target.value)}/>
                </div>

                <div>
                    <h3 className="mt-6">Is Administrator</h3>
                    <input type="checkbox" className="default:ring-2" value={user.isAdmin}
                           onChange={e => setIsAdmin(e.currentTarget.checked)}
                           checked={isAdmin ? 'checked' : ''}/>
                </div>

                <div className="m-8">
                    <h3 className="">Mobile</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' value={mobile} onChange={e => setMobile(e.target.value)}/>
                </div>
            </div>

            <div className="absolute right-0">
               <span
                   className='bg-blue-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800'>
                <ButtonTxt name={'save'} onClick={onSave}/>
                           </span>

                <span
                    className="bg-blue-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                <ButtonTxt name={'Cancel'} onClick={onCancel}/>
                    </span>
            </div>


        </>
    );
};

export default EditUser;