# AI-CareerGuide

## Project Overview

AI-CareerGuide is an AI-powered career planning and development guidance system designed for the University of Nottingham Ningbo China (UNNC). The project leverages machine learning and big data to provide personalized career recommendations, customized learning paths, job market trend analysis, and internship/employment matching for students. By analyzing students' interests, skills, academic performance, and personality traits, combined with historical career data and real-time labor market trends, the system aims to enhance student employability and optimize university resources.

## Objectives

- Deliver personalized career guidance using AI and machine learning.
- Provide tailored learning paths and skill enhancement recommendations.
- Offer AI-powered internship and job matching with employers.
- Analyze job market trends to align student preparation with industry demands.
- Improve the university's career services and resource utilization.

## Features

- **Machine Learning Algorithm**: Trained on historical student data to provide tailored career suggestions.
- **Job Market Trend Analysis**: Insights into sectoral growth and demand.
- **Custom Learning Paths**: Recommended resources for skill development.
- **Internship and Job Matching**: AI-driven matching with employers.
- **Data Security**: Encrypted storage and transmission with strict access controls.

## Repository Structure

- `/data`: Placeholder for datasets (e.g., student profiles, graduate destinations, market trends). Note: Actual data is not included due to privacy concerns.
- `/models`: Machine learning models and training scripts (e.g., based on DeepSeek-r1).
- `/src`: Source code for the AI system, including data processing, model training, and recommendation logic.
- `/docs`: Project documentation, including the project brief, training scheme, and presentations.
- `/scripts`: Utility scripts for data preprocessing, model fine-tuning, and deployment.
- `/tests`: Unit and integration tests for the system.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Bill092738/AI-CareerGuide.git
   ```
2. Install dependencies (example for Python-based components):

   ```bash
   pip install -r requirements.txt
   ```
3. Set up the server environment:
    - Ensure a CUDA-enabled NVIDIA GPU (A6000 or above).
    - Install AI frameworks like PyTorch or TensorFlow.
    - Ubuntu24 recommend
4. Configure data sources and environment variables (see `/docs/setup.md` for details).

## Usage

1. **Data Preparation**:
   - Collect and preprocess student data (e.g., grades, personality tests, internship records).
   - Store data securely with encryption and access controls.
2. **Model Training**:
   - Fine-tune the DeepSeek-r1 model using scripts in `/scripts`.
   - Run pre-training and supervised fine-tuning (SFT) as outlined in `/docs/training_scheme.pdf`.
3. **Running the System**:
   - Deploy the AI recommendation system on a compatible server.
   - Use the provided API endpoints to generate career recommendations and learning paths.
4. **Prompt Optimization**:
   - Customize prompts for specific career queries and adjust the temperature parameter for response diversity.

## Requirements

- **Hardware**:
  - Server with NVIDIA A6000 or higher-grade CUDA-enabled GPU.
  - Sufficient RAM to support PyTorch/TensorFlow frameworks.
- **Software**:
  - Python 3.8+.
  - AI frameworks: PyTorch, TensorFlow.
  - Data processing libraries: pandas, NumPy.
- **Data**:
  - Student personal information, academic records, personality test results, internship experiences, and graduate destinations.
- **Legal/Support**:
  - Compliance with data privacy regulations (supported by UNNC's assurance office).
  - Faculty instructor support for curriculum integration.

## Contributing

Contributions are welcome! The project is led and supported by the following team members:

- **Project Leader**: 葛帅晨 Shuaichen Ge (20616658)
- **General Planner**: 于桐歌 Tongge Yu (20616067)
- **Technicians**:
  - 夏浩铭 Haoming Xia (20614873)
  - 曹江昊 Jianghao Cao (20616327)
  - 赵浩然 Haoran Zhao (20617234) (Github: Bill092738)

To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

- **Organization**: University of Nottingham Ningbo China
- **Email**: saysg6@nottingham.edu.cn

## Acknowledgments

- University of Nottingham Ningbo China, Careers and Employability Service Office.
- Project team and stakeholders for their support and feedback.