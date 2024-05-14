
import {useHistory, useParams} from "react-router-dom";
import ButtonTxt from "./ButtonTxt";

import {useEffect, useState} from "react";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import axios from "axios";


const EditCompany = (props) => {
    props.func('Edit Company');
    props.showAdd(false);

    const {id} = useParams();
    const history = useHistory();

    const [name, setName] = useState('');
    const [department, setDepartment] = useState('');
    const [street, setStreet] = useState('');
    const [zipCode, setZipCode] = useState('');
    const [city, setCity] = useState('');
    const [country, setCountry] = useState('');

    const [company, setCompany] = useState([]);
    const [setCompanies] = useState([]);

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
        axios.get(`${APP_API_ENDPOINT_URL}/companies/${id}`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                setCompany(response.data);
            });
    }, []);



    useEffect(() => {
        setName(company.name);
        setDepartment(company.department);
        setStreet(company.street);
        setZipCode(company.zipCode);
        setCity(company.city);
        setCountry(company.country);
    }, [company]);


    const onSave = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';

        const updatedCompany = {
            id: null,
            name: name,
            department: department,
            street: street,
            zipCode: zipCode,
            city: city,
            country: country,
            jwt: null
        };

        axios.put(`${APP_API_ENDPOINT_URL}/companies/${id}`, updatedCompany, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                if (response.status === 200) {
                history.push(routes.companies);}
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
    " type='text' value={name} onChange={e => setName(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">department</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' value={department} onChange={e => setDepartment(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">street</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' value={street} onChange={e => setStreet(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">zip_code</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' value={zipCode} onChange={e => setZipCode(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">City</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' value={city} onChange={e => setCity(e.target.value)}/></div>
                <div className="m-3">
                    <h3 className="text-center">Country</h3>
                    <input className="block border m-auto text-sm text-slate-500
    " type='text' value={country} onChange={e => setCountry(e.target.value)}/></div>
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

export default EditCompany;