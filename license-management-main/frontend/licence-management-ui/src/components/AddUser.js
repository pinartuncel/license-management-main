import axios from "axios";
import {useState, useEffect} from 'react';
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";

const AddUser = (props) => {
    props.func('Add User');
    props.showAdd(false);

    const history = useHistory();
    const handleError = () => {
        console.log("something went wrong");
    }

    const [username, setUsername] = useState('');
    const [isAdmin, setIsAdmin] = useState(false);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [mobile, setMobile] = useState('');

    const [listIndex, setListIndex] = useState(0);
    const [companies, setCompanies] = useState([]);

    useEffect(() => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
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
    }, []);

    const onCancel = () => {
        history.push(routes.users);
    }

    const onSave = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';

        const newUser = {
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

        axios.post(`${APP_API_ENDPOINT_URL}/users`, newUser, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            }
        }).then((response) => {
            if (response.status === 200) {
                history.push(routes.users)
            } else handleError();

        });

    }

    return (
        <>
            <div className="m-4">
                <h3 className="">Company</h3>
                <select onChange={e => setListIndex(e.target.value)}>
                    {companies.map((companyitem, i) => <option
                        value={i} key={companyitem.id}>{companyitem.name}</option>)}
                </select>
            </div>

            <div className="m-4">
                <h3 className="">Username</h3>
                <input className="block border text-sm text-slate-500
    " type='text' placeholder='Select a username' value={username} onChange={e => setUsername(e.target.value)}/>
            </div>

            <div className="grid grid-cols-2 p-2">

                <div className="m-4">
                    <h3 className="">First Name</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' placeholder='' value={firstName} onChange={e => setFirstName(e.target.value)}/>
                </div>

                <div className="m-4">
                    <h3 className="">Last Name</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' placeholder='' value={lastName} onChange={e => setLastName(e.target.value)}/>
                </div>

                <div className="m-4">
                    <h3 className="">Email</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' placeholder='' value={email} onChange={e => setEmail(e.target.value)}/>
                </div>

                <div className="m-4">
                    <h3 className="">Phone</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' placeholder='' value={phone} onChange={e => setPhone(e.target.value)}/>
                </div>


                <div>
                    <h3 className="mt-6">Grant admin rights</h3>
                    <input type="checkbox" value={isAdmin} className="default:ring-2"
                           onChange={e => setIsAdmin(e.currentTarget.checked)}/>
                </div>

                <div className="m-4">
                    <h3 className="">Mobile</h3>
                    <input className="block border text-sm text-slate-500
    " type='text' placeholder='' value={mobile} onChange={e => setMobile(e.target.value)}/>
                </div>
            </div>

            <div className="absolute right-0">
                <button type='button'
                        className="bg-blue-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        onClick={onSave}>
                    Save
                </button>

                <button type='button'
                        className="bg-blue-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        onClick={onCancel}>
                    Cancel
                </button>
            </div>


        </>
    );
};

export default AddUser;