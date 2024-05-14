import axios from "axios";
import Contract from './Contract';
import {useEffect, useState} from "react";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";

const Contracts = ({func, showAdd, searchText}) => {
    func('Contracts');
    showAdd(true);

    const history = useHistory();
    const [contracts, setContracts] = useState([]);

    const reloadCallback = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.get(`${APP_API_ENDPOINT_URL}/contracts`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                let filter_data = response.data.filter(value => Object.values(value).find(item => String(item).toLowerCase().includes(searchText.toLowerCase())) !== undefined)
                setContracts(filter_data)
            });
    }

    useEffect(() => {
        reloadCallback();
    }, []);

    return (
        <>
            <div className='contract flex justify-items-start py-2 border-b-2'>
                <span className='tbl-row tbl-head'>Company</span>
                <span className='tbl-row tbl-head'>Date start</span>
                <span className='tbl-row tbl-head'>Date end</span>
                <span className='tbl-row tbl-head'>Version</span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='clearfix'></span>
            </div>
            {contracts ?
                contracts.map((contract) => (
                    <Contract key={contract.id} contract={contract} reloadCallback={reloadCallback}/>
                ))
                : console.log("No contracts in list")
            }
        </>
    );
};

export default Contracts;
