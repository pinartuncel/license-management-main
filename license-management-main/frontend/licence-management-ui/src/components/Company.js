import ButtonTxt from './ButtonTxt';
import axios from "axios";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from 'react-router-dom';


const Company = ({company, reloadCallback}) => {
    const history = useHistory();

    // Do something when API calls return ERROR
    const handleError = () => {
        console.log("something went wrong");
    }

    const onDelete = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.delete(`${APP_API_ENDPOINT_URL}/companies/${company.id}`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                Authorization: `Bearer ${jwt}`,
            },
        })
            .then((response) => {
                if (response.status === 200) {
                    reloadCallback();
                } else if (response.status === 401) handleError();
            })
    };
    const onEdit = () => {
        history.push(`${routes.editcompany}/${company.id}`);

    };
    const onUsers = () => {
        history.push(`${routes.usersbycompany}/${company.id}`);
    };
    const onContracts = () => {
        history.push(`${routes.contractsbycompany}/${company.id}`);
    };

    return (
        <>
            <div className='company border-b-2'>
                <span className='tbl-row tbl-content'>{company.name}</span>
                <span className='tbl-row tbl-content'>{company.department}</span>
                <span className='tbl-row tbl-content'>{company.street}</span>
                <span className='tbl-row tbl-content'>{company.zipCode}</span>
                <span className='tbl-row tbl-content'>{company.city}</span>
                <span className='tbl-row tbl-content'>{company.country}</span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
          <ButtonTxt name={'Edit'} onClick={onEdit}/>
        </span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
          <ButtonTxt name={'Delete'} onClick={onDelete}/>
        </span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
          <ButtonTxt name={'Contracts'} onClick={onContracts}/>
        </span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
          <ButtonTxt name={'Users'} onClick={onUsers}/>
        </span>
            </div>
        </>
    );
};

export default Company;
