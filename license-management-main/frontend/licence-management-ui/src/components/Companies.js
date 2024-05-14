import axios from 'axios';
import Company from './Company';
import {useEffect, useState} from 'react';
import {APP_API_ENDPOINT_URL, routes} from '../config';
import {useHistory} from 'react-router-dom';

const Companies = ({func, showAdd, searchText}) => {
    func('Companies');
    showAdd(true);


    const history = useHistory();
    const [companies, setCompanies] = useState([]);

    const reloadCallback = () => {
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
                let filter_data = response.data.filter(value => Object.values(value).find(item => String(item).toLowerCase().includes(searchText.toLowerCase())) !== undefined)
                setCompanies(filter_data)
            });
    }


    useEffect(() => {
        reloadCallback();
    }, []);

    return (
        <>
            <div className='company border-b-2'>
                <span className='tbl-row tbl-head'>Name</span>
                <span className='tbl-row tbl-head'>Department</span>
                <span className='tbl-row tbl-head'>Street</span>
                <span className='tbl-row tbl-head'>ZIP</span>
                <span className='tbl-row tbl-head'>City</span>
                <span className='tbl-row tbl-head'>Country</span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='tbl-row tbl-head headTxt'></span>
                <span className='clearfix'></span>
            </div>
            {companies ?
                companies.map((company) => (
                    <Company key={company.id} company={company} reloadCallback={reloadCallback}/>
                ))
                : console.log("No companies in list")
            }
        </>
    );
};

export default Companies;
