import ButtonTxt from "./ButtonTxt";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";
import axios from "axios";

const User = ({user, reloadCallback}) => {

    const history = useHistory();

    // Do something when API calls return ERROR
    const handleError = () => {
        console.log("something went wrong");
    }

    const onEdit = () => {
        history.push(`${routes.edituser}/${user.id}`);
    };


    const onDelete = () => {
        let actualuser = JSON.parse(localStorage.getItem('user'));
        if (actualuser === null) {
            history.push(routes.login);
            return;
        }
        let jwt = actualuser.jwt || '';
        axios.delete(`${APP_API_ENDPOINT_URL}/users/${user.id}`, {
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


    return (
        <>
            <div className='user flex justify-items-start py-2 border-b-2'>
                <span className='tbl-row tbl-content'>{user.company.name}</span>
                <span className='tbl-row tbl-content'>{user.username}</span>
                <span className='tbl-row tbl-content'>{user.firstName}</span>
                <span className='tbl-row tbl-content'>{user.lastName}</span>
                <span className='tbl-row tbl-content'>{user.email}</span>
                <span className='tbl-row tbl-content'>{user.phone}</span>
                <span className='tbl-row tbl-content'>{user.mobile}</span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
                <ButtonTxt name={'Edit'} onClick={onEdit}/>
                </span>
                <span className='tbl-row tbl-content btnTxt onHoverChangeCol'>
                <ButtonTxt name={'Delete'} onClick={onDelete}/>
                </span>
            </div>
        </>
    );
};

export default User;
