"""Raspberry Pi Firebase Utilities

Functions:
    get_firebase_app: Returns firebase db reference with given root url
    write_measurement: Writes a new measurement entry to database
    get_measurement: Retrieves measurement with given unique id m_id
    remove_measurement: Removes measurement with given unique id m_id

Attributes:
    ROOT_URL: Root url for firebase database
    MEASUREMENTS: Path for measurements tree

Exceptions:

Todo:
    *
"""

import firebase as firebase

_ROOT_URL = 'https://safe-8cca1.firebaseio.com/'
_MEASUREMENTS = '/measurements'

def get_firebase_app(root_url = _ROOT_URL):
    """Returns firebase db reference with given root_url

    Returns:
        FirebaseApplication: firebase db

    Args:
        root_url (str): firebase root url
    """
    return firebase.FirebaseApplication(root_url, None);

def write_measurement(distance, image, time):
    """Writes a new measurement entry to database

    Args:
        distance (str): measured distance
        image (str): Base-64 encoding of associated image
        time (str): time of recording data
    """
    firebase = get_firebase_app()
    result = firebase.post(_MEASUREMENTS, {'distance': distance, 'image': image, 'time':time})
    # do some kind of error handling here on result

def get_measurement(m_id):
    """Returns dictionary of measurement attributes for measurement with given unique id m_id

    Returns:
        dict[str:str]: attributes for measurement with given m_id

    Args:
        m_id (str): unique id
    """
    firebase = get_firebase_app()
    attributes = firebase.get(_MEASUREMENTS, m_id)
    return attributes

def remove_measurement(m_id):
    """Removes measurement with given unique id m_id

    Args:
        m_id (str): unique id
    """
    firebase = get_firebase_app()
    firebase.delete(_MEASUREMENTS, m_id)
