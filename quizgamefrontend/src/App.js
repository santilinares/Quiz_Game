import logo from './logo.svg';
import './App.css';
import React, {useState, useEffect} from 'react';

function App() {
    const [questions, setQuestions] = useState([]);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [isRecording, setIsRecording] = useState(false);
    const [timer, setTimer] = useState(10);

    useEffect(() => {
        // Fetch questions from the JSON file
        const fetchQuestions = async () => {
            try {
                const response = await fetch('./src/data/questions/questions.json');
                const data = await response.json();
                setQuestions(data);
            } catch (error) {
                console.error('Error fetching questions:', error);
            }
        };

        fetchQuestions();
    }, []);

    const startRecording = () => {
        // Implement your logic to start recording
        console.log('Recording started');
        setIsRecording(true);
    };

    const stopRecording = () => {
        // Implement your logic to stop recording
        console.log('Recording stopped');
        setIsRecording(false);

        // Assume recording.wav is saved at "./src/data/answerRecordings/"
        // You can trigger the backend check here
        const isCorrect = true // Implement checkAnswer() logic

        // Display correct or incorrect message
        alert(isCorrect ? 'Correct' : 'Incorrect');

        // Move to the next question after a delay
        setTimeout(() => {
            setCurrentQuestionIndex(prevIndex => (prevIndex + 1) % questions.length);
            setTimer(10); // Reset timer
        }, 10000);
    };

    useEffect(() => {
        // Timer for resetting recording button
        const interval = setInterval(() => {
            setTimer(prevTimer => prevTimer - 1);
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    return (
        <div>
            <h1>Quiz Game</h1>
            <div>
                <h3>Question {currentQuestionIndex + 1}</h3>
                <p>{questions[currentQuestionIndex]?.question}</p>
            </div>
            <button onClick={isRecording ? stopRecording : startRecording}>
                {isRecording ? 'Stop Recording' : 'Start Recording'}
            </button>
            <p>Time remaining: {timer} seconds</p>
        </div>
    );
}

export default App;
