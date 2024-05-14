import ButtonTxt from "./ButtonTxt";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";
import axios from "axios";

const Contract = ({contract, reloadCallback}) => {
    const history = useHistory();

    // Do something when API calls return ERROR
    const handleError = () => {
        console.log("something went wrong");
    }
    const onEdit = () => {

        history.push(`${routes.editcontract}/${contract.id}`);
    };

    const onDelete = () => {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user === null) {
            history.push(routes.login);
            return;
        }
        let jwt = user.jwt || '';
        axios.delete(`${APP_API_ENDPOINT_URL}/contracts/${contract.id}`, {
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
    const onDetails = () => {
        history.push(`${routes.details}/${contract.id}`);
    };
    return (
        <>
            <div className='contract flex justify-items-start py-2 border-b-2'>
                <span className='tbl-row tbl-content'>{contract.company.name}</span>
                <span className='tbl-row tbl-content'>{contract.dateStart.toString().substring(0, 10)}</span>
                <span className='tbl-row tbl-content'>{contract.dateStop.toString().substring(0, 10)}</span>
                <span className='tbl-row tbl-content'>{contract.version}</span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
        <ButtonTxt name={'Edit'} onClick={onEdit}/>
        </span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
          <ButtonTxt name={'Delete'} onClick={onDelete}/>
        </span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
          <ButtonTxt name={'Details'} onClick={onDetails}/>
        </span>
            </div>
        </>
    );
};

export default Contract;
