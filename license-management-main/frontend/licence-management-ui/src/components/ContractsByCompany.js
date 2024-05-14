import axios from "axios";
import Contract from "./Contract";
import {useState, useEffect} from "react";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory, useParams} from "react-router-dom";

const ContractsByCompany = (props) => {
    props.func('Contracts by company');
    props.showAdd(false);

    const {id} = useParams();

    const history = useHistory();
    const [contracts, setContracts] = useState([]);


    useEffect(() => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.get(`${APP_API_ENDPOINT_URL}/companies/${id}/contracts`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                setContracts(response.data);
            });
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
                    <Contract key={contract.id} contract={contract}/>
                ))
                : console.log("No contracts in list")
            }
        </>
    );
};

export default ContractsByCompany;