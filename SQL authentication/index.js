const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const app = express();
const knex = require('knex');
const database = knex({
	client: 'pg',
	connection: {
		host: '127.0.0.1',
		user: 'postgres',
		password: 'kbosedbl',
		database: 'android'
	}
});

database.select('*').from('login');

app.use(bodyParser.json());

app.post('/login' , (req , res) => {
	database.select('email','password').from('login')
		.where('email' , '=' , req.body.email)
		.then(data => {
			if(req.body.password === data[0].password)
			{
				return database.select('*').from('login')
					.where('email' , '=' , req.body.email)
					.then((user) => {
						res.status(400).json("Logged in Successfully");
						console.log('Successfully signed in');
					})
					.catch((err) => {
						res.status(200).json("Incorrect");
						console.log('User not found');
					})
			}
			else
			{
				res.status(400).json("Unable to login");
				console.log('Unable to login');
			}
		})
		.catch(err => {
			res.status(200).json('Incorrect');
			console.log('Unable to login');
		});
});

app.post(('/register') , (req , res) => {
	const {email,password,name} = req. body;
	database('login')
		.returning('*')
		.insert({
			email: email,
			name: name,
			password: password
		})
		.then(user => {
			console.log('Successfull registration');
			res.status(400).json("Registered Successfully");
		})
		.catch(err => {
			console.log('Registration failed')
			res.status(400).json("Registration failed");
		})
});

app.listen(3000, () => {
    console.log("Listening on port 3000...")
});