import pyrebase

config          = {
    'apiKey'                : "AIzaSyBq9j3Fd9cO6ox6OV9hMKjhPy7_aUpjHqU",
    'authDomain'            : "hackathon-team-13.firebaseapp.com",
    'databaseURL'           : "https://hackathon-team-13.firebaseio.com",
    'projectId'             : "hackathon-team-13",
    'storageBucket'         : "",
    'messagingSenderId'     : "780498370702",
    'appId'                 : "1:780498370702:web:b006adf31cceaa69"
}

# initialize app with config
firebase    = pyrebase.initialize_app(config)

# authenticate a user
auth        = firebase.auth()
user        = auth.sign_in_with_email_and_password("team13infosys@gmail.com", "jetbrains")

db          = firebase.database()