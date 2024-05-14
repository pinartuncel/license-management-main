import PropTypes from 'prop-types';

const ButtonTxt = ({ name, icon, onClick }) => {
  return (
    <button alt={name} onClick={onClick}>
      {icon}
    </button>
  );
};

ButtonTxt.propTypes = {
  name: PropTypes.string,
  linkTo: PropTypes.string,
  icon: PropTypes.element,
  onClick: PropTypes.func,
};

export default ButtonTxt;
