#include <string>
#include <iostream>
using namespace std;
enum Type {NUM,STRING,ARRAY};
class Variable
{
    public:
        Variable();
        ~Variable() {};
        explicit Variable(const Variable& v)
        {
            flag = v.getFlague();
            arraySize = v.getArraySize();
            if(flag == NUM)
                intValue = v.getIntValue();
            else if(flag == STRING)
                stringValue = v.getStringValue();
            else if(flag == ARRAY)
            {
                string tmp[40];
                for(int i = 0; i<v.getArraySize();++i)
                    tmp[i] = v.getArrayValue(i);

                std::copy(tmp,tmp+arraySize,arrayValue);

            }
        }

        Variable(int v)
        {
            intValue = v;
            flag = NUM;
        }

        Variable(string v)
        {
            flag = STRING;
            stringValue = v;
        }

        Variable(string s[],int size)
        {
            arraySize = size;
            std::copy(s,s+size,arrayValue);
            flag = ARRAY;
        }

        void setValue(const Variable& v)
        {
            flag = v.getFlague();
            arraySize = v.getArraySize();
            if(flag == NUM)
                intValue = v.getIntValue();
            else if(flag == STRING)
                stringValue = v.getStringValue();
            else if(flag == ARRAY)
            {
                string tmp[40];
                for(int i = 0; i<v.getArraySize();++i)
                    tmp[i] = v.getArrayValue(i);

                std::copy(tmp,tmp+arraySize,arrayValue);
            }
        }

        void setValue(int v)
        {
            flag = NUM;
            intValue = v;
        }

        void setValue(string v)
        {
            flag = STRING;
            stringValue = (string) v;
        }

        void setValue(string s [], int size)
        {
            arraySize = size;
            string tmp[40];
            for(int i = 0; i<size;++i)
                tmp[i] = s[i];

            std::copy(tmp,tmp+arraySize,arrayValue);
            flag = ARRAY;
        }

        Type getFlague() const
        {
            return flag;
        }

        int getIntValue() const
        {
            return intValue;
        }

        string getStringValue() const
        {
            return stringValue;
        }

        int getArraySize() const
        {
            return arraySize;
        }

        string getArrayValue(int i) const
        {
            return arrayValue[i];
        }

        Variable& operator=(const Variable& v)
        {
            if(this == &v)
                return *this;

            if(v.getFlague() == NUM)
              intValue = v.getIntValue();

            else if(v.getFlague() == STRING)
                stringValue = v.getStringValue();

            else if(v.getFlague() == ARRAY)
            {
                arraySize = v.getArraySize();
                string tmp[40];

                for(int i = 0; i<arraySize;++i)
                  tmp[i] = v.getArrayValue(i);


                std::copy(tmp,tmp+arraySize,arrayValue);
            }

            flag = v.getFlague();
            return *this;
        }
    friend ostream& operator<<(ostream& os, const Variable&);
    private:
        Type flag; //if flag == true, then object is numeric, otherwise is text
        int intValue;
        string stringValue;
        string arrayValue[40];
        int arraySize;

};
ostream& operator<<(ostream&os, const Variable& v)
{
    if(v.flag == NUM)
        os<<v.intValue;
    else if(v.flag == STRING)
        os<<v.stringValue;
    else if(v.flag == ARRAY)
    {
        for(int i = 0; i<v.arraySize;++i)
            os<<v.arrayValue[i]<<endl;
    }
    return os;
}

const bool operator<(const Variable& v1, const Variable& v2)
{
    if(v1.getFlague() == NUM && v2.getFlague() == NUM)
        return (v1.getIntValue() < v2.getIntValue());
    else return
        true;
}
const bool operator>(const Variable& v1, const Variable& v2)
{
    if(v1.getFlague() == NUM && v2.getFlague() == NUM)
        return (v1.getIntValue() > v2.getIntValue());
    else return
        true;
}

const int operator+(const Variable& v1, const Variable& v2)
{
    if(v1.getFlague() == NUM && v2.getFlague() == NUM)
        return (v1.getIntValue() + v2.getIntValue());
    else
        return 2;
}

const int operator-(const Variable& v1, const Variable& v2)
{
    if(v1.getFlague() == NUM && v2.getFlague() == NUM)
        return (v1.getIntValue() -v2.getIntValue());
    else
        return 0;
}

const int operator*(const Variable& v1, const Variable& v2)
{
    if(v1.getFlague() == NUM && v2.getFlague() == NUM)
        return (v1.getIntValue() * v2.getIntValue());
    else
        return 2;
}

const int operator/(const Variable& v1, const Variable& v2)
{
    if(v1.getFlague() == NUM && v2.getFlague() == NUM)
        return (v1.getIntValue() / v2.getIntValue());
    else
        return 2;
}
int main()
{
    Variable a(5);
    Variable b("Abc");
    if((a+5)>5)
        cout<<"HELLO";
}
