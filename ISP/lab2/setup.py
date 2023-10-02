from setuptools import setup

setup(
    name='serializer',
    version='1.0',
    packages=['serializer'],
    entry_points={
        'console_scripts': [
            'serializer-cli=serializer.console:main'
        ]
    }
)