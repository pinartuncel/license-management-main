import ButtonTxt from "./ButtonTxt";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

const Details = (props) => {
    props.func('Contract details');
    props.showAdd(false);

    const {id} = useParams();
    const history = useHistory();

    const [contract, setContract] = useState(undefined);
    
    useEffect(() => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.get(`${APP_API_ENDPOINT_URL}/contracts/${id}`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                if (response != undefined && response != null) {
                    setContract(response.data);
                }
            });
    }, []);


    const onClose = () => {
        history.push(routes.contracts)
    };

    return (
        <>
            <div className="grid grid-cols-2 p-10 m-auto text-enter ">
                <div className="m-3">
                    <h3 className="text-center">Company</h3>
                    <input className="block border m-auto text-sm text-slate-500"
                           type='text' value={contract && contract.company.name}/></div>

                <div className="m-3">
                    <h3 className="text-center">Responsible person 1</h3>
                    <input className="block border m-auto text-sm text-slate-500"
                           type='text'  value={contract && contract.user1 && contract.user1.username}/></div>


                <div className="m-3">
                    <h3 className="text-center">Responsible person 2</h3>
                    <input className="block border m-auto text-sm text-slate-500"
                           type='text'  value={contract && contract.user2 && contract.user2.username}/></div>


                <div className="m-3">
                    <h3 className="text-center">Date Start</h3>
                    <input className="block border m-auto text-sm text-slate-500"
                           type='date' value={contract && contract.dateStart.split('T')[0]}
                    />
                </div>
                <div className="m-3">
                    <h3 className="text-center">Date End</h3>
                    <input className="block border m-auto text-sm text-slate-500"
                           type='date' value={contract && contract.dateStop.split('T')[0]}
                    /></div>
                <div className="m-3">
                    <h3 className="text-center">Version</h3>
                    <input className="block border m-auto text-sm text-slate-500"
                           type='text'  value={contract && contract.version}/></div>
                <div className="m-3">
                    <h3 className="text-center">IP number 1</h3>
                    <input className="block border m-auto text-sm text-slate-500" type='text'
                           value={contract && contract.ipaddress1}
                    /></div>
                <div className="m-3">
                    <h3 className="text-center">Feature A</h3>
                    <input className="block border m-auto text-sm text-slate-500" type='text'
                           value={contract && contract.feature1}/></div>
                <div className="m-3">
                    <h3 className="text-center">IP number 2</h3>
                    <input className="block border m-auto text-sm text-slate-500" type='text'
                           value={contract && contract.ipaddress2}
                    /></div>
                <div className="m-3">
                    <h3 className="text-center">Feature B</h3>
                    <input className="block border m-auto text-sm text-slate-500" type='text'
                           value={contract && contract.feature2}/></div>
                <div className="m-3">
                    <h3 className="text-center">IP number 3</h3>
                    <input className="block border m-auto text-sm text-slate-500" type='text'
                           value={contract && contract.ipaddress3}
                    /></div>
                <div className="m-3">
                    <h3 className="text-center">Feature C</h3>
                    <input className="block border m-auto text-sm text-slate-500" type='text'
                           value={contract && contract.feature3}/></div>
                <div className="m-3">
                    <h3 className="text-center">License key</h3>
                    <input className="block border m-auto left-30 w-full text-sm text-slate-500" type='text'
                            value={contract && contract.licenseKey}/></div>
            </div>


            <div className="absolute right-20">
        <span
            className="bg-blue-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
    <ButtonTxt name={'Close'} onClick={onClose}/>
            </span>
            </div>


        </>
    );
};

export default Details;
