import ButtonTxt from "./ButtonTxt";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";
import {useState} from "react";
import axios from "axios";


const AddCompany = (props) => {
    props.func('Add Company');
    props.showAdd(false);

    const history = useHistory();
    const handleError = () => {
        console.log("something went wrong");
    }
    const [name, setName] = useState('');
    const [department, setDepartment] = useState('');
    const [street, setStreet] = useState('');
    const [zipCode, setZipCode] = useState('');
    const [city, setCity] = useState('');
    const [country, setCountry] = useState('');


    const onSave = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';

        const newcompany = {
            id: null,
            name: name,
            department: department,
            street: street,
            zipCode: zipCode,
            city: city,
            country: country
        };
        console.log(newcompany);
        axios.post(`${APP_API_ENDPOINT_URL}/companies`, newcompany, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            }
        }).then((response) => {
            if (response.status === 200) {
                history.push(routes.companies)
            } else handleError();

        });

    }

    const onCancel = () => {
        history.push(routes.companies)
    }

    return (
        <>
            <div className="grid grid-cols-2 p-10 m-auto text-enter ">
                <div className="m-3">
                    <h3 className="text-center">Name</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' placeholder='' value={name} onChange={e => setName(e.target.value)}/>
                </div>
                <div className="m-3">
                    <h3 className="text-center">department</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' placeholder='' value={department} onChange={e => setDepartment(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">street</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' placeholder='' value={street} onChange={e => setStreet(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">zip_code</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' placeholder='' value={zipCode} onChange={e => setZipCode(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">City</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' placeholder='' value={city} onChange={e => setCity(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">Country</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' placeholder='' value={country} onChange={e => setCountry(e.target.value)}/></div>
            </div>

            <div className="absolute right-20">
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

export default AddCompany;